package ru.tsvlad.wayd_user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsvlad.wayd_user.restapi.dto.JwtPayload;
import ru.tsvlad.wayd_user.entity.UserEntity;
import ru.tsvlad.wayd_user.service.JwtService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService<JwtPayload> {

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.prefix}")
    private String prefix;

    private final Base64.Decoder decoder = Base64.getDecoder();

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

        return prefix + " " + Jwts.builder()
                .setPayload(payloadStr)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public Jws<Claims> validateJwt(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);
    }

    @Override
    public JwtPayload deserializeTokenTo(String token, Class<JwtPayload> clazz) throws JsonProcessingException {
        String body = token.split("\\.")[1];
        String jsonBody = new String(decoder.decode(body), StandardCharsets.UTF_8);
        return objectMapper.readValue(jsonBody, JwtPayload.class);
    }
}
