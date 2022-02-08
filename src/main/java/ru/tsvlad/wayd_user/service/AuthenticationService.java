package ru.tsvlad.wayd_user.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    String getUserId(Authentication authentication);
}
