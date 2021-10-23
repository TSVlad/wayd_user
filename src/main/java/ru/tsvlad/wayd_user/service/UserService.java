package ru.tsvlad.wayd_user.service;

import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForOwnerDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;

public interface UserService {
    UserForOwnerDTO registerUser(UserDTO userDTO);
    UserForOwnerDTO updateUser(UserForUpdateDTO userDTO);
    void updateValidBadWords(long id, boolean isValid);
}
