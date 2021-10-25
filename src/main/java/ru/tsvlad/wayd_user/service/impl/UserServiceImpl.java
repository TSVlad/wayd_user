package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.UserStatus;
import ru.tsvlad.wayd_user.messaging.producer.UserServiceProducer;
import ru.tsvlad.wayd_user.repo.ConfirmationCodeRepository;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.EmailAlreadyExistsException;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.UsernameAlreadyExistsException;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForOwnerDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserPublicDTO;
import ru.tsvlad.wayd_user.service.ConfirmationCodeService;
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

    @Override
    @Transactional
    public UserForOwnerDTO registerUser(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.registerUser(userDTO);

        checkSameEmail(userEntity.getEmail());
        checkSameUsername(userEntity.getUsername());

        UserEntity result = userRepository.save(userEntity);
//        userServiceProducer.registerAccount(MappingUtils.map(result, UserPublicDTO.class));
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
            throw new RuntimeException();
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.updateUser(userDTO);
        UserEntity result = userRepository.save(userEntity);
        userServiceProducer.updateAccount(MappingUtils.map(result, UserPublicDTO.class));
        return MappingUtils.map(result, UserForOwnerDTO.class);
    }

    @Override
    @Transactional
    public void updateValidBadWords(long id, boolean isValid) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isEmpty()) {
                throw new RuntimeException();
            }
            UserEntity userEntity = userEntityOptional.get();
            userEntity.setValidBadWords(isValid);
            userRepository.save(userEntity);
        } catch (OptimisticLockingFailureException exception) {
            updateValidBadWords(id, isValid);
        }
    }
}
