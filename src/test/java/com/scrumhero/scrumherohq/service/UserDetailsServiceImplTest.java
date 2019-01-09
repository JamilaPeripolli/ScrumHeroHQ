package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.impl.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.UserFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {UserDetailsServiceImpl.class})
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl service;

    @MockBean
    private UserRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadByUsernameShouldReturnUser() {
        Optional<User> expected = Optional.of(create(AuthorityType.USER));
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(expected);

        UserDetails user = service.loadUserByUsername("player@mail.com");

        assertThat(user).isEqualTo(expected.get());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsernameShouldThrowExceptionWhenUsernameNotFound() {
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.empty());

        service.loadUserByUsername("player@mail.com");
    }
}
