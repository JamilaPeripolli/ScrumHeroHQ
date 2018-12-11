package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.impl.UserServiceImpl;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {UserServiceImpl.class})
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSave() throws BadRequestException {
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn(Mockito.anyString());
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.empty());

        service.save(createTestUserDto(AuthorityType.USER));

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowExceptionWhenUserIsDuplicated() throws BadRequestException {
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn(Mockito.anyString());
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.of(new User()));

        service.save(createTestUserDto(AuthorityType.USER));

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void shouldReturnUser() {
        Optional<User> expected = Optional.of(createTestUser(AuthorityType.USER));
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(expected);

        UserDetails user = service.loadUserByUsername("player@mail.com");

        assertThat(user).isEqualTo(expected.get());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowException() {
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.empty());

        service.loadUserByUsername("player@mail.com");
    }

    private UserDto createTestUserDto(AuthorityType authority) {
        UserDto user = new UserDto();
        user.setName("player");
        user.setEmail("player@mail.com");
        user.setPassword("1234");
        user.setAuthority(authority);

        return user;
    }

    private User createTestUser(AuthorityType authority) {
        User user = new User();
        user.setId(1L);
        user.setName("player");
        user.setEmail("player@mail.com");
        user.setPassword("1234");
        user.setAuthority(authority);

        return user;
    }

}