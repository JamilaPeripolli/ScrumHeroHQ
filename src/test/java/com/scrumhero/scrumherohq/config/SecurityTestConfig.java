package com.scrumhero.scrumherohq.config;

import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                if ("player@mail.com".equals(email)) {
                    return createUser(email, AuthorityType.USER);
                } else if ("admin@mail.com".equals(email)) {
                    return createUser(email, AuthorityType.ADMIN);
                } else {
                    throw new UsernameNotFoundException(email);
                }
            }
        };
    }

    private UserDetails createUser(String email, AuthorityType authority) {
        return new User(1L, "Player", email, "", authority);
    }
}
