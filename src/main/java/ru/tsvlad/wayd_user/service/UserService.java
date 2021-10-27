package ru.tsvlad.wayd_user.service;

import ru.tsvlad.wayd_user.enums.Validity;
import ru.tsvlad.wayd_user.restapi.dto.*;

public interface UserService {
    UserForOwnerDTO registerUser(UserForRegisterDTO userDTO);
    UserForOwnerDTO updateUser(UserForUpdateDTO userDTO);
    void confirmEmail(ConfirmationCodeDTO codeDTO);
    void updateValidBadWords(long id, Validity validity);
}
