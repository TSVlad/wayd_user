package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsvlad.wayd_user.commons.OrganizationRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserUpdateInfo;
import ru.tsvlad.wayd_user.enums.Role;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.ForbiddenException;
import ru.tsvlad.wayd_user.restapi.dto.*;
import ru.tsvlad.wayd_user.service.AuthenticationService;
import ru.tsvlad.wayd_user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;
    private AuthenticationService authenticationService;

    private ModelMapper modelMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser(@RequestBody UserRegisterDTO userDTO) {
        return modelMapper.map(
                userService.registerUser(modelMapper.map(userDTO, UserRegisterInfo.class)), UserDTO.class
        );
    }

    @Secured("ROLE_MODERATOR")
    @PostMapping("/register/organization")
    public UserDTO registerOrganization(@RequestBody OrganizationRegisterDTO organizationRegisterDTO) {
        return modelMapper.map(
                userService.registerOrganization(modelMapper.map(organizationRegisterDTO, OrganizationRegisterInfo.class)),
                UserDTO.class
        );
    }

    @Secured("ROLE_USER")
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO updateUser(@RequestBody UserForUpdateDTO userForUpdateDTO,
                              Authentication authentication) {
        String userId = authenticationService.getUserId(authentication);
        if (!userId.equals(userForUpdateDTO.getId())) {
            throw new ForbiddenException();
        }
        return modelMapper.map(
                userService.updateUser(modelMapper.map(userForUpdateDTO, UserUpdateInfo.class)),
                UserDTO.class
        );
    }

    @GetMapping("/for-username/{username}/{pageNumber}/{pageSize}")
    public Page<UserPublicDTO> getAllByUsername(@PathVariable String username, @PathVariable int pageNumber,
                                                @PathVariable int pageSize) {
        return userService.getAllActiveByUsername(username, pageNumber, pageSize)
                .map(user -> modelMapper.map(user, UserPublicDTO.class));
    }

    @PostMapping("/by-ids")
    public List<UserPublicDTO> getAllByIds(@RequestBody List<String> ids) {
        return userService.getAllByIds(ids).stream()
                .map(user -> modelMapper.map(user, UserPublicDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserPublicDTO getUserById(@PathVariable String id) {
        return modelMapper.map(userService.getUserById(id), UserPublicDTO.class);
    }
}
