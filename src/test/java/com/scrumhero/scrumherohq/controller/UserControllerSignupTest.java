package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.config.SecurityTestConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.fixture.UserFixture;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.service.UserService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;

import static com.scrumhero.scrumherohq.fixture.UserFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { UserController.class })
@ContextConfiguration(classes = {SecurityTestConfig.class})
public class UserControllerTest extends AbstractControllerTest {

    private static final String USERS_ENDPOINT = "/api/users";

    @MockBean
    private UserService service;

    @Test
    public void signupShouldReturn201() throws Exception {
        Mockito.when(service.save(Mockito.any())).thenReturn(createDto());

        UserDto user = createDto();
        user.setId(null);

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(createDto()));
    }

    @Test
    public void signupShouldReturn400WhenNameIsMissing() throws Exception {
        UserDto user = createDto();
        user.setId(null);
        user.setName("");

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void signupShouldReturn400WhenEmailIsMissing() throws Exception {
        UserDto user = createDto();
        user.setId(null);
        user.setEmail("");

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void signupShouldReturn400WhenEmailIsInvalid() throws Exception {
        UserDto user = createDto();
        user.setEmail("t3st&*%@$t3st");

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void signupShouldReturn400WhenPasswordIsMissing() throws Exception {
        UserDto user = createDto();
        user.setId(null);
        user.setPassword("");

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void signupShouldReturn400WhenAuthorityIsMissing() throws Exception {
        UserDto user = createDto();
        user.setAuthority(null);

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/signup"), HttpMethod.POST, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn200() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenReturn(UserFixture.createDto());

        UserDto user = UserFixture.createDto();

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(UserFixture.createDto()));
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn400WhenNameIsMissing() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenReturn(UserFixture.createDto());

        UserDto user = UserFixture.createDto();
        user.setName("");

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn400WhenNameIsDuplicated() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenThrow(BadRequestException.class);

        UserDto user = UserFixture.createDto();

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenThrow(ResourceNotFoundException.class);

        UserDto user = UserFixture.createDto();

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(user));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void getAllShouldReturn200AndBody() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(UserFixture.createDto()));

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT, HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(Arrays.asList(UserFixture.createDto())));
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void getAllShouldReturn200() throws Exception {
        Mockito.when(service.getAll()).thenReturn(new ArrayList<>());

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT, HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void getOneShouldReturn200() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(UserFixture.createDto());

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(UserFixture.createDto()));
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void getOneShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.when(service.getById(1L)).thenThrow(ResourceNotFoundException.class);

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void deleteShouldReturn204() throws Exception {
        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.DELETE, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void deleteShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(1L);

        MockHttpServletResponse response = doRequest(USERS_ENDPOINT.concat("/1"), HttpMethod.DELETE, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}