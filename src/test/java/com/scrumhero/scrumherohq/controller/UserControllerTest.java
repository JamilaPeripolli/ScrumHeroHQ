package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.service.UserService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { UserController.class })
public class UserControllerTest extends AbstractControllerTest {

    private static final String SIGN_UP_ENDPOINT = "/api/user/signup";

    @MockBean
    private UserService service;

    @Test
    public void shouldReturnCreated() throws Exception {
        UserDto user = createTestUserDto();
        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnBadRequestWhenMissingName() throws Exception {
        UserDto user = createTestUserDto();
        user.setName(null);

        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnBadRequestWhenMissingEmail() throws Exception {
        UserDto user = createTestUserDto();
        user.setEmail(null);

        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        UserDto user = createTestUserDto();
        user.setEmail("t3st&*%@$t3st");

        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnBadRequestWhenMissingPassword() throws Exception {
        UserDto user = createTestUserDto();
        user.setPassword(null);

        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldReturnBadRequestWhenMissingAuthority() throws Exception {
        UserDto user = createTestUserDto();
        user.setAuthority(null);

        MockHttpServletResponse response = doRequest(SIGN_UP_ENDPOINT, HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private UserDto createTestUserDto() {
        UserDto user = new UserDto();
        user.setName("player1");
        user.setEmail("player1@mail.com");
        user.setPassword("1234");
        user.setAuthority(AuthorityType.USER);

        return user;
    }

}
