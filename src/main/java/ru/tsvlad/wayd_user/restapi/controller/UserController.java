package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsvlad.wayd_user.restapi.dto.UserDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForOwnerDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForRegisterDTO;
import ru.tsvlad.wayd_user.restapi.dto.UserForUpdateDTO;
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

    @PutMapping
    public ResponseEntity<UserForOwnerDTO> updateUser(@RequestBody UserForUpdateDTO userForUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(userForUpdateDTO));
    }
}
