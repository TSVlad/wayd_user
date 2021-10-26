package ru.tsvlad.wayd_user.service.impl;

import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.enums.Role;
import ru.tsvlad.wayd_user.repo.RoleRepository;
import ru.tsvlad.wayd_user.service.RoleService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    private final  RoleRepository roleRepository;
    private final Map<Role, RoleEntity> rolesMap;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        rolesMap = new HashMap<>();
        roleRepository.findAll().forEach(role -> rolesMap.put(role.getName(), role));
    }

    @Override
    public RoleEntity getRoleEntityByName(Role name) {
        return rolesMap.get(name);
    }
}
