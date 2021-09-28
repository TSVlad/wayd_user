package ru.tsvlad.wayd_user.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.tsvlad.wayd_user.data.dto.UsernamePasswordDTO;

public interface AuthenticationService {
    String loginAndGetToken(@RequestBody UsernamePasswordDTO usernamePasswordDTO);
}
