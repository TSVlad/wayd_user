package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.Role;
import ru.tsvlad.wayd_user.repo.RoleRepository;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.NotFoundException;
import ru.tsvlad.wayd_user.service.RoleService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final  RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final Map<Role, RoleEntity> rolesMap;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        rolesMap = new HashMap<>();
        roleRepository.findAll().forEach(role -> rolesMap.put(role.getName(), role));
    }

    @Override
    public RoleEntity getRoleEntityByName(Role name) {
        return rolesMap.get(name);
    }

    @Override
    public void addRoleToUser(Role role, long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }
        UserEntity userEntity = userOptional.get();
        userEntity.getRoles().add(getRoleEntityByName(role));
        userRepository.save(userEntity);
    }
}
