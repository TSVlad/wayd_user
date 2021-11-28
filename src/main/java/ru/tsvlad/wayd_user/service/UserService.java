package ru.tsvlad.wayd_user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.enums.Validity;
import ru.tsvlad.wayd_user.restapi.dto.*;

import java.util.List;

public interface UserService {
    Page<UserPublicDTO> getAllByUsername(Pageable pageable, String str);
    List<UserPublicDTO> getAllById(List<Long> id);
    UserEntity getUserById(long id);

    UserForOwnerDTO registerUser(UserForRegisterDTO userDTO);
    UserForOwnerDTO updateUser(UserForUpdateDTO userDTO);
    boolean confirmEmail(ConfirmationCodeDTO codeDTO);
    void updateValidBadWords(long id, Validity validity);
    void banUser(long userId);
    void unbanUser(long userId);
}
