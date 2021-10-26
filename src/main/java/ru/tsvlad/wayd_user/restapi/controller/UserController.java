package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<HttpStatus> confirmEmail(@RequestBody  ConfirmationCodeDTO codeDTO) {
        userService.confirmEmail(codeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserForOwnerDTO> updateUser(@RequestBody UserForUpdateDTO userForUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(userForUpdateDTO));
    }
}
