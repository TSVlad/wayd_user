package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.data.dto.UsernamePasswordDTO;
import ru.tsvlad.wayd_user.data.entity.UserEntity;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.service.AuthenticationService;
import ru.tsvlad.wayd_user.service.JwtService;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String loginAndGetToken(UsernamePasswordDTO usernamePasswordDTO) {
        UserEntity user = userRepository.findByUsername(usernamePasswordDTO.getUsername());
        if (user == null) {
            throw new RuntimeException();//
        }

        if (!passwordEncoder.matches(usernamePasswordDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException();//
        }

        return jwtService.generateToken(user);
    }
}
