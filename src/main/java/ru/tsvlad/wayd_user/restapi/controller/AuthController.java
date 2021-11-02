package ru.tsvlad.wayd_user.restapi.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsvlad.wayd_user.restapi.dto.ChangePasswordDTO;
import ru.tsvlad.wayd_user.restapi.dto.JwtPayload;
import ru.tsvlad.wayd_user.restapi.dto.UsernamePasswordDTO;
import ru.tsvlad.wayd_user.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authorization. Token will be returned"),
            @ApiResponse(responseCode = "401", description = "Authorization failed. Empty String will be returned")
    })
    public ResponseEntity<String> loginAndGetToken(@RequestBody UsernamePasswordDTO usernamePasswordDTO) {
        return new ResponseEntity<>(authenticationService.loginAndGetToken(usernamePasswordDTO), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
        JwtPayload jwtPayload = (JwtPayload) authentication.getPrincipal();
        authenticationService.changePassword(jwtPayload.getId(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
