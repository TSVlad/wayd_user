package ru.tsvlad.wayd_user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.data.dto.JwtPayload;
import ru.tsvlad.wayd_user.data.entity.UserEntity;
import ru.tsvlad.wayd_user.service.JwtService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("jwt.secret")
    private String jwtSecret;

    private final ObjectMapper objectMapper;

    @Autowired
    public JwtServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String generateToken(UserEntity userEntity) {
        LocalDate expirationDate = LocalDate.now().plus(30, ChronoUnit.DAYS);
        JwtPayload payload = new JwtPayload(userEntity);
        payload.setExpiredAt(expirationDate);
        String payloadStr;

        try {
            payloadStr = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error(e.getOriginalMessage());
            payloadStr = "";
        }

        return Jwts.builder()
                .setPayload(payloadStr)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
