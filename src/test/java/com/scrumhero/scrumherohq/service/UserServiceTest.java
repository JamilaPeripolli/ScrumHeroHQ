package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.model.User;
import com.scrumhero.scrumherohq.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTest() {
        userService.save(new User());

        Mockito.verify(userRepository, Mockito.only()).save(Mockito.any());
    }

    @Test
    public void deleteTest() {
        userService.delete(new User());

        Mockito.verify(userRepository, Mockito.only()).delete(Mockito.any());
    }

    @Test
    public void findForLoginTest() {
        String email = "john@email.com";
        String password = "123Change";

        User user = new User(1L, "John", email, password);
        Mockito.when(userRepository.findByUsernameAndPassword(email, password)).thenReturn(user);

        User result = userService.findForLogin(email, password);

        Mockito.verify(userRepository, Mockito.only()).findByUsernameAndPassword(email, password);
        Assert.assertNotNull(result);
        Assert.assertEquals(user.getId(), result.getId());
        Assert.assertEquals(user.getName(), result.getName());
        Assert.assertEquals(user.getEmail(), result.getEmail());
        Assert.assertEquals(user.getPassword(), result.getPassword());
    }

}
