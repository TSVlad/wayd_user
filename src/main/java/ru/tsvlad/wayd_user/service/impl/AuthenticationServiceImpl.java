package ru.tsvlad.wayd_user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.NotFoundException;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.UnsuccessfulLoginException;
import ru.tsvlad.wayd_user.restapi.dto.UsernamePasswordDTO;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.repo.UserRepository;
import ru.tsvlad.wayd_user.service.AuthenticationService;
import ru.tsvlad.wayd_user.service.JwtService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String loginAndGetToken(UsernamePasswordDTO usernamePasswordDTO) {
        UserEntity user = userRepository.findByUsername(usernamePasswordDTO.getUsername());
        if (user == null || usernamePasswordDTO.getPassword() == null) {
            throw new UnsuccessfulLoginException();
        }

        if (!passwordEncoder.matches(usernamePasswordDTO.getPassword(), user.getPassword())) {
            throw new UnsuccessfulLoginException();
        }

        return jwtService.generateToken(user);
    }

    @Override
    public void changePassword(long id, String oldPassword, String newPassword) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException();
        }
        UserEntity userEntity = userOptional.get();
        userEntity.changePassword(oldPassword, newPassword);
        userRepository.save(userEntity);
    }
}
