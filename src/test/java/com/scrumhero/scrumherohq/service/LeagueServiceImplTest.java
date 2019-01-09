package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.LeagueDto;
import com.scrumhero.scrumherohq.repository.LeagueRepository;
import com.scrumhero.scrumherohq.service.impl.LeagueServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.LeagueFixture.create;
import static com.scrumhero.scrumherohq.fixture.LeagueFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {LeagueServiceImpl.class})
public class LeagueServiceImplTest {

    @Autowired
    private LeagueService service;

    @MockBean
    private LeagueRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnLeague() throws BadRequestException {
        Mockito.when(repository.findByName("First Team")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        LeagueDto league = createDto();
        league.setId(null);
        LeagueDto result = service.save(league);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenLeagueIsDuplicated() throws BadRequestException {
        Mockito.when(repository.findByName("First Team")).thenReturn(Optional.of(create()));

        LeagueDto league = createDto();
        league.setId(null);

        service.save(league);
    }

    @Test
    public void updateShouldReturnLeague() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        LeagueDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenLeagueIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenLeagueIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findByNameWhereIdIsNotEquals("First Team", 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfLeague() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<LeagueDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<LeagueDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnLeague() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        LeagueDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenLeagueIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        LeagueDto result = service.getById(1L);
    }

    @Test
    public void deleteShouldCallRepository() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteShouldThrowExceptionWhenLeagueIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
