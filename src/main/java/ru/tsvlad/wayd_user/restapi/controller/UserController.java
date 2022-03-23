package ru.tsvlad.wayd_user.restapi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsvlad.wayd_user.commons.OrganizationRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserRegisterInfo;
import ru.tsvlad.wayd_user.commons.UserUpdateInfo;
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
        log.debug("Register user request gotten");
        return modelMapper.map(
                userService.registerUser(modelMapper.map(userDTO, UserRegisterInfo.class)), UserDTO.class
        );
    }

    @Secured("ROLE_MODERATOR")
    @PostMapping("/register/organization")
    public UserDTO registerOrganization(@RequestBody OrganizationRegisterDTO organizationRegisterDTO) {
        log.debug("Register organization request gotten for {}", organizationRegisterDTO);
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
        log.debug("Update user request gotten: {}", userForUpdateDTO);
        String userId = authenticationService.getUserId(authentication);
        if (!userId.equals(userForUpdateDTO.getId())) {
            throw new ForbiddenException();
        }
        return modelMapper.map(
                userService.updateUser(modelMapper.map(userForUpdateDTO, UserUpdateInfo.class)),
                UserDTO.class
        );
    }

    @GetMapping
    public Page<UserPublicDTO> getAll(@RequestParam(required = false, defaultValue = "") String searchString, @RequestParam int page, @RequestParam int size) {
        log.debug("Get all users request gotten: page {}, size{}", page, size);
        return userService.getAllActiveByUsername(searchString, page, size)
                .map(user -> modelMapper.map(user, UserPublicDTO.class));
    }

    @PostMapping("/by-ids")
    public List<UserPublicDTO> getAllByIds(@RequestBody List<String> ids) {
        log.debug("Get all users by ids request gotten: ids {}", ids);
        return userService.getAllByIds(ids).stream()
                .map(user -> modelMapper.map(user, UserPublicDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserPublicDTO getUserById(@PathVariable String id) {
        log.debug("Get user by id request gotten: id {}", id);
        return modelMapper.map(userService.getUserById(id), UserPublicDTO.class);
    }
}
