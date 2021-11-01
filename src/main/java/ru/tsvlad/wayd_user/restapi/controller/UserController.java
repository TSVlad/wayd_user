package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.ForbiddenException;
import ru.tsvlad.wayd_user.restapi.dto.*;
import ru.tsvlad.wayd_user.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserForOwnerDTO> registerUser(@RequestBody UserForRegisterDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<Boolean> confirmEmail(@RequestBody  ConfirmationCodeDTO codeDTO) {
        return new ResponseEntity<>(userService.confirmEmail(codeDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserForOwnerDTO> updateUser(@RequestBody UserForUpdateDTO userForUpdateDTO, Authentication authentication) {
        System.out.println(authentication);
        JwtPayload jwtPayload = (JwtPayload) authentication.getPrincipal();
        if (jwtPayload.getId() != userForUpdateDTO.getId()) {
            throw new ForbiddenException();
        }
        return ResponseEntity.ok(userService.updateUser(userForUpdateDTO));
    }
}
