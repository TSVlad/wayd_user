package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForOwnerDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
import ru.tsvlad.wayd_user.service.UserService;
import ru.tsvlad.wayd_user.utils.MappingUtils;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserForOwnerDTO registerUser(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.registerUser(userDTO);
        return MappingUtils.map(userRepository.save(userEntity), UserForOwnerDTO.class);
    }

    @Override
    public UserForOwnerDTO updateUser(UserForUpdateDTO userDTO) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO.getId());
        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException();
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.updateUser(userDTO);
        return MappingUtils.map(userRepository.save(userEntity), UserForOwnerDTO.class);
    }
}
