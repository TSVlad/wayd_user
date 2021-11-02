package ru.tsvlad.wayd_user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.tsvlad.wayd_user.enums.Validity;
import ru.tsvlad.wayd_user.restapi.dto.*;

import java.util.List;

public interface UserService {
    Page<UserPublicDTO> getAllByUsername(Pageable pageable, String str);
    List<UserPublicDTO> getAllById(List<Long> id);

    UserForOwnerDTO registerUser(UserForRegisterDTO userDTO);
    UserForOwnerDTO updateUser(UserForUpdateDTO userDTO);
    boolean confirmEmail(ConfirmationCodeDTO codeDTO);
    void updateValidBadWords(long id, Validity validity);
}
