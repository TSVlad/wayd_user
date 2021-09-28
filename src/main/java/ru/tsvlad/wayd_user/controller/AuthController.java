package ru.tsvlad.wayd_user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsvlad.wayd_user.data.dto.UsernamePasswordDTO;
import ru.tsvlad.wayd_user.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> loginAndGetToken(@RequestBody UsernamePasswordDTO usernamePasswordDTO) {
        return new ResponseEntity<>(authenticationService.loginAndGetToken(usernamePasswordDTO), HttpStatus.OK);
    }
}
