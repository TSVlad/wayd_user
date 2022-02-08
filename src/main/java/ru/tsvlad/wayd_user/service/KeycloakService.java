package ru.tsvlad.wayd_user.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import ru.tsvlad.wayd_user.commons.OrganizationRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserUpdateInfo;

import java.util.List;

public interface KeycloakService {
    UserRepresentation addUser(UserRegisterInfo userRegisterInfo);
    UserRepresentation addOrganization(OrganizationRegisterInfo organizationRegisterInfo);
    UserRepresentation updateUser(UserUpdateInfo userUpdateInfo);
    Page<UserRepresentation> getUsersWithUsernameLike(String username, int page, int size);
    List<UserRepresentation> getUsersByIds(List<String> ids);
    UserRepresentation getUserById(String id);
}
