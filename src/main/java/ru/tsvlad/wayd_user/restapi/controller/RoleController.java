package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsvlad.wayd_user.enums.Role;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
//    private final RoleService roleService;

    /*@PostMapping("/to-user/{id}")
    public ResponseEntity<HttpStatus> giveModeratorRoleToUser(@PathVariable long id) {
        roleService.addRoleToUser(Role.ROLE_MODERATOR, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
