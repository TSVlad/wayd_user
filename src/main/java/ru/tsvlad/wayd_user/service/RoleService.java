package ru.tsvlad.wayd_user.service;


import ru.tsvlad.wayd_user.entity.RoleEntity;
import ru.tsvlad.wayd_user.enums.Role;

public interface RoleService {
    RoleEntity getRoleEntityByName(Role name);
}
