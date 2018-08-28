package com.scrumhero.scrumherohq.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumhero.scrumherohq.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private final long EXPIRATION_TIME = 3600000;

    private final String SECRET = "U2NydW1IZXJvSFE="; // Base64 ScrumHeroHQ
    private final String HEADER_NAME = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: tratar exceção
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                            .setSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                            .signWith(SignatureAlgorithm.HS512, SECRET)
                            .compact();
        response.addHeader(HEADER_NAME, TOKEN_PREFIX + token);
    }
}
