package ru.tsvlad.wayd_user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import ru.tsvlad.wayd_user.entity.UserEntity;

public interface JwtService<T> {
    String generateToken(UserEntity userEntity);
    Jws<Claims> validateJwt(String token);
    T deserializeTokenTo(String token, Class<T> clazz) throws JsonProcessingException;
}
