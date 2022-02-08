package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsvlad.wayd_user.commons.OrganizationRegisterInfo;
import ru.tsvlad.wayd_user.commons.User;
import ru.tsvlad.wayd_user.commons.UserRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserUpdateInfo;
import ru.tsvlad.wayd_user.commons.mapper.KeycloakMapper;
import ru.tsvlad.wayd_user.messaging.dto.UserKafkaDTO;
import ru.tsvlad.wayd_user.messaging.producer.UserServiceProducer;
import ru.tsvlad.wayd_user.restapi.dto.UserPublicDTO;
import ru.tsvlad.wayd_user.service.KeycloakService;
import ru.tsvlad.wayd_user.service.UserService;
import ru.tsvlad.wayd_user.utils.MappingUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserServiceProducer userServiceProducer;

    private KeycloakService keycloakService;

    private final ModelMapper modelMapper;
    private final KeycloakMapper keycloakMapper;

    @Override
    public Page<User> getAllActiveByUsername(String username, int page, int size) {
        return keycloakService.getUsersWithUsernameLike(username, page, size).map(keycloakMapper::toUser);
    }

    @Override
    public List<User> getAllByIds(List<String> ids) {
        return keycloakService.getUsersByIds(ids).stream().map(keycloakMapper::toUser).collect(Collectors.toList());
    }

    @Override
    public User getUserById(String id) {
        return keycloakMapper.toUser(keycloakService.getUserById(id));
    }

    @Override
    @Transactional
    public User registerUser(UserRegisterInfo userRegisterInfo) {
        UserRepresentation userRepresentation = keycloakService.addUser(userRegisterInfo);
        User result = keycloakMapper.toUser(userRepresentation);
        userServiceProducer.registerAccount(modelMapper.map(result, UserKafkaDTO.class));
        return result;
    }

    @Override
    @Transactional
    public User registerOrganization(OrganizationRegisterInfo organizationRegisterInfo) {
        UserRepresentation userRepresentation = keycloakService.addOrganization(organizationRegisterInfo);
        User result = keycloakMapper.toUser(userRepresentation);
        //TODO: producer
        return result;

        /*UserEntity userEntity = UserEntity.registerOrganization(organizationRegisterDTO, password, roleService);
        UserEntity result = userRepository.save(userEntity);
        userServiceProducer.organizationRegistered(EmailCredentialsDTO.builder()
                .userId(result.getId())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .password(password)
                .build());
        return MappingUtils.map(result, UserDTO.class);*/
    }

    @Override
    @Transactional
    public User updateUser(UserUpdateInfo userUpdateInfo) {
        UserRepresentation userRepresentation = keycloakService.updateUser(userUpdateInfo);
        User user = keycloakMapper.toUser(userRepresentation);
        return user;

        /*Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getId());
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
        userServiceProducer.updateAccount(MappingUtils.map(result, UserKafkaDTO.class));
        return MappingUtils.map(result, UserForOwnerDTO.class);*/
    }

    /*@Override
    @Transactional
    public boolean confirmEmail(ConfirmationCodeDTO codeDTO) {
        boolean success = confirmationCodeService.confirm(codeDTO);
        if (success) {
            UserEntity userEntity = userRepository.findByEmail(codeDTO.getEmail());
            userEntity.confirmEmail();
            userRepository.save(userEntity);

        }
        return success;
    }

    @Override
    @Transactional
    public void updateValidBadWords(long id, Validity validity) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isEmpty()) {
                throw new NotFoundException();
            }
            UserEntity userEntity = userEntityOptional.get();
            userEntity.updateValidBadWords(validity);
            userRepository.save(userEntity);
        } catch (OptimisticLockingFailureException exception) {
            updateValidBadWords(id, validity);
        }
    }

    @Override
    @Transactional
    public void banUser(long userId) {
        UserEntity userEntity = getUserById(userId);
        userEntity.ban();
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void unbanUser(long userId) {
        UserEntity userEntity = getUserById(userId);
        userEntity.unban();
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void addRoleToUser(long userId, Role role) {
        UserEntity userEntity = getUserById(userId);
        userEntity.getRoles().add(roleService.getRoleEntityByName(role));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteRoleFromUser(long userId, Role role) {
        UserEntity userEntity = getUserById(userId);
        userEntity.getRoles().remove(roleService.getRoleEntityByName(role));
        userRepository.save(userEntity);
    }
*/

}
