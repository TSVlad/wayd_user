package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.UserStatus;
import ru.tsvlad.wayd_user.enums.Validity;
import ru.tsvlad.wayd_user.messaging.producer.UserServiceProducer;
import ru.tsvlad.wayd_user.repo.ConfirmationCodeRepository;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.EmailAlreadyExistsException;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.ForbiddenException;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.NotFoundException;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.UsernameAlreadyExistsException;
import ru.tsvlad.wayd_user.restapi.dto.*;
import ru.tsvlad.wayd_user.service.ConfirmationCodeService;
import ru.tsvlad.wayd_user.service.RoleService;
import ru.tsvlad.wayd_user.service.UserService;
import ru.tsvlad.wayd_user.utils.MappingUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ConfirmationCodeRepository confirmationCodeRepository;

    private UserServiceProducer userServiceProducer;
    private ConfirmationCodeService confirmationCodeService;
    private RoleService roleService;

    @Override
    @Transactional
    public UserForOwnerDTO registerUser(UserForRegisterDTO userDTO) {
        UserEntity userEntity = UserEntity.registerUser(userDTO, roleService);

        checkSameEmail(userEntity.getEmail());
        checkSameUsername(userEntity.getUsername());

        UserEntity result = userRepository.save(userEntity);
        confirmationCodeService.createConfirmationCodeForEmail(result.getEmail());
        return MappingUtils.map(result, UserForOwnerDTO.class);
    }

    private void checkSameEmail(String email) {
        UserEntity existingUserWithSameEmail = userRepository.findByEmail(email);

        if (existingUserWithSameEmail != null) {
            if (existingUserWithSameEmail.getStatus() == UserStatus.NOT_APPROVED_EMAIL
                    && confirmationCodeRepository.findAllByEmailAndExpirationAfter(existingUserWithSameEmail.getEmail(),
                    LocalDateTime.now()).isEmpty()) {
                userRepository.delete(existingUserWithSameEmail);
            } else {
                throw new EmailAlreadyExistsException();
            }
        }
    }

    private void checkSameUsername(String username) {
        UserEntity existingUserWithSameUsername = userRepository.findByUsername(username);

        if (existingUserWithSameUsername != null) {
            if (existingUserWithSameUsername.getStatus() == UserStatus.NOT_APPROVED_EMAIL
                    && confirmationCodeRepository.findAllByEmailAndExpirationAfter(existingUserWithSameUsername.getEmail(),
                    LocalDateTime.now()).isEmpty()) {
                userRepository.delete(existingUserWithSameUsername);
            } else {
                throw new UsernameAlreadyExistsException();
            }
        }
    }

    @Override
    @Transactional
    public UserForOwnerDTO updateUser(UserForUpdateDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getId());
        if (userEntityOptional.isEmpty()) {
            throw new NotFoundException();
        }
        UserEntity userEntity = userEntityOptional.get();

        if (userEntity.getStatus() == UserStatus.NOT_APPROVED_EMAIL
                || userEntity.getStatus() == UserStatus.NOT_APPROVED_BY_MODERATOR) {
            throw new ForbiddenException();
        }

        userEntity.updateUser(userDTO);
        UserEntity result = userRepository.save(userEntity);
        userServiceProducer.updateAccount(MappingUtils.map(result, UserWithoutPasswordDTO.class));
        return MappingUtils.map(result, UserForOwnerDTO.class);
    }

    @Override
    @Transactional
    public boolean confirmEmail(ConfirmationCodeDTO codeDTO) {
        boolean success = confirmationCodeService.confirm(codeDTO);
        if (success) {
            UserEntity userEntity = userRepository.findByEmail(codeDTO.getEmail());
            userEntity.confirmEmail();
            userRepository.save(userEntity);
            userServiceProducer.registerAccount(MappingUtils.map(userEntity, UserWithoutPasswordDTO.class));
        }
        return success;
    }

    @Override
    @Transactional
    public void updateValidBadWords(long id, Validity validity) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isEmpty()) {
                throw new RuntimeException();
            }
            UserEntity userEntity = userEntityOptional.get();
            userEntity.updateValidBadWords(validity);
            userRepository.save(userEntity);
        } catch (OptimisticLockingFailureException exception) {
            updateValidBadWords(id, validity);
        }
    }
}
