package ru.tsvlad.wayd_user.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.InvalidTokenException;
import ru.tsvlad.wayd_user.restapi.dto.JwtPayload;
import ru.tsvlad.wayd_user.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    @Value("${jwt.prefix}")
    private String prefix;

    private final JwtService<JwtPayload> jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null) {
            try {
                jwtService.validateJwt(token);
            } catch (Exception e) {
                throw new InvalidTokenException();
            }
            JwtPayload jwtPayload;
            try {
                jwtPayload = jwtService.deserializeTokenTo(token, JwtPayload.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            List<SimpleGrantedAuthority> authorities = jwtPayload.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtPayload, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
