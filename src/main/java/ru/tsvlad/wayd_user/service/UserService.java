package ru.tsvlad.wayd_user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.tsvlad.wayd_user.commons.OrganizationRegisterInfo;
import ru.tsvlad.wayd_user.commons.User;
import ru.tsvlad.wayd_user.commons.UserRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserUpdateInfo;
import ru.tsvlad.wayd_user.restapi.dto.*;

import java.util.List;

public interface UserService {
    Page<User> getAllActiveByUsername(String username, int page, int size);
    List<User> getAllByIds(List<String> id);
    User getUserById(String id);

    User registerUser(UserRegisterInfo userRegisterInfo);
    User registerOrganization(OrganizationRegisterInfo organizationRegisterInfo);
    User updateUser(UserUpdateInfo userUpdateInfo);

    /*void updateValidBadWords(long id, Validity validity);
    void banUser(long userId);
    void unbanUser(long userId);
    void addRoleToUser(long userId, Role role);
    void deleteRoleFromUser(long userId, Role role);*/
}
