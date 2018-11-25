package com.scrumhero.scrumherohq.config.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.scrumhero.scrumherohq.util.Constants.SECURITY_TOKEN_HEADER;
import static com.scrumhero.scrumherohq.util.Constants.SECURITY_TOKEN_PREFIX;

@Component
public class JwtFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    @Value("${app.security.token.key}")
    private String key;

    @Autowired
    public JwtFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SECURITY_TOKEN_HEADER);

        if (header == null || !header.startsWith(SECURITY_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(getToken(header));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private String getToken(String header) {
        return header.replace(SECURITY_TOKEN_PREFIX, "").trim();
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (token != null) {
            String principal = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (principal != null) {
                UserDetails user = userDetailsService.loadUserByUsername(principal);
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }
        }
        return null;
    }
}