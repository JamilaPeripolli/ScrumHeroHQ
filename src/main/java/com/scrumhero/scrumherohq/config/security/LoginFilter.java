package com.scrumhero.scrumherohq.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrumhero.scrumherohq.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.scrumhero.scrumherohq.util.Constants.SECURITY_TOKEN_HEADER;
import static com.scrumhero.scrumherohq.util.Constants.SECURITY_TOKEN_PREFIX;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${app.security.token.expirationTime:3600000}")
    private long expirationTime;

    @Value("${app.security.token.key}")
    private String key;

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {

            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        } catch (IOException e) {
            throw new BadCredentialsException("Could not authenticate the user, please try again.", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        response.addHeader(SECURITY_TOKEN_HEADER, SECURITY_TOKEN_PREFIX + token);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setAuthenticationManager(authenticationManager);
    }
}