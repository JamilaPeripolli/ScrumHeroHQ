package com.scrumhero.scrumherohq.controller;

import com.scrumhero.scrumherohq.config.SecurityTestConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.LeagueDto;
import com.scrumhero.scrumherohq.service.impl.LeagueServiceImpl;
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

import static com.scrumhero.scrumherohq.fixture.LeagueFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { LeagueController.class })
@ContextConfiguration(classes = {SecurityTestConfig.class})
public class LeagueControllerTest extends AbstractControllerTest {

    private static final String LEAGUE_ENDPOINT = "/api/leagues";

    @MockBean
    private LeagueServiceImpl service;

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void createShouldReturn201() throws Exception {
        Mockito.when(service.save(Mockito.any())).thenReturn(createDto());

        LeagueDto league = createDto();
        league.setId(null);

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT, HttpMethod.POST, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(createDto()));
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void createShouldReturn400WhenNameIsMissing() throws Exception {
        Mockito.when(service.save(Mockito.any())).thenReturn(createDto());

        LeagueDto league = createDto();
        league.setId(null);
        league.setName("");

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT, HttpMethod.POST, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void createShouldReturn400WhenNameIsDuplicated() throws Exception {
        Mockito.when(service.save(Mockito.any())).thenThrow(BadRequestException.class);

        LeagueDto league = createDto();

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT, HttpMethod.POST, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn200() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenReturn(createDto());

        LeagueDto league = createDto();

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(createDto()));
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn400WhenNameIsMissing() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenReturn(createDto());

        LeagueDto league = createDto();
        league.setName("");

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn400WhenNameIsDuplicated() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenThrow(BadRequestException.class);

        LeagueDto league = createDto();

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void updateShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.when(service.update(Mockito.any())).thenThrow(ResourceNotFoundException.class);

        LeagueDto league = createDto();

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.PUT, createJson(league));

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithUserDetails(value = "player@mail.com")
    public void getAllShouldReturn200AndBody() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(createDto()));

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT, HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(Arrays.asList(createDto())));
    }

    @Test
    @WithUserDetails(value = "player@mail.com")
    public void getAllShouldReturn200() throws Exception {
        Mockito.when(service.getAll()).thenReturn(new ArrayList<>());

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT, HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithUserDetails(value = "player@mail.com")
    public void getOneShouldReturn200() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(createDto());

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(createJson(createDto()));
    }

    @Test
    @WithUserDetails(value = "player@mail.com")
    public void getOneShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.when(service.getById(1L)).thenThrow(ResourceNotFoundException.class);

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.GET, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void deleteShouldReturn204() throws Exception {
        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.DELETE, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithUserDetails(value = "admin@mail.com")
    public void deleteShouldReturn404WhenResourceIsNotFound() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(1L);

        MockHttpServletResponse response = doRequest(LEAGUE_ENDPOINT.concat("/1"), HttpMethod.DELETE, null);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}
