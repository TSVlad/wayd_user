package ru.tsvlad.wayd_user.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.tsvlad.wayd_user.restapi.dto.UsernamePasswordDTO;

public interface AuthenticationService {
    String loginAndGetToken(UsernamePasswordDTO usernamePasswordDTO);
    void changePassword(long id, String oldPassword, String newPassword);
}
