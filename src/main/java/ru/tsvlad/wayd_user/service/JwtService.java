package ru.tsvlad.wayd_user.service;

import ru.tsvlad.wayd_user.entity.UserEntity;

public interface JwtService {
    String generateToken(UserEntity userEntity);
}
