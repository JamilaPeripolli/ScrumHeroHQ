package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;
import com.scrumhero.scrumherohq.repository.IntergalacticMissionRepository;
import com.scrumhero.scrumherohq.service.impl.IntergalacticMissionServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.IntergalacticMissionFixture.create;
import static com.scrumhero.scrumherohq.fixture.IntergalacticMissionFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {IntergalacticMissionServiceImpl.class})
public class IntergalacticMissionServiceImplTest {

    @Autowired
    private IntergalacticMissionService service;

    @MockBean
    private IntergalacticMissionRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnIntergalacticMission() throws BadRequestException {
        Mockito.when(repository.findByName("Project 1")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        IntergalacticMissionDto intergalacticMission = createDto();
        intergalacticMission.setId(null);
        intergalacticMission.setStatus(null);

        IntergalacticMissionDto result = service.save(intergalacticMission);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenIntergalacticMissionIsDuplicated() throws BadRequestException {
        Mockito.when(repository.findByName("Project 1")).thenReturn(Optional.of(create()));

        IntergalacticMissionDto intergalacticMission = createDto();
        intergalacticMission.setId(null);
        intergalacticMission.setStatus(null);

        service.save(intergalacticMission);
    }

    @Test
    public void updateShouldReturnIntergalacticMission() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        IntergalacticMissionDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test
    public void updateShouldNotOverwriteAllFields() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        IntergalacticMissionDto dto = createDto();
        dto.setEndDate(LocalDate.now());
        dto.setStartDate(LocalDate.now());
        dto.setStatus(null);

        IntergalacticMissionDto result = service.update(dto);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenIntergalacticMissionIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenIntergalacticMissionIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findByNameWhereIdIsNotEquals("Project 1", 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfIntergalacticMission() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<IntergalacticMissionDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<IntergalacticMissionDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnIntergalacticMission() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        IntergalacticMissionDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenIntergalacticMissionIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        IntergalacticMissionDto result = service.getById(1L);
    }

    @Test
    public void deleteShouldCallRepository() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteShouldThrowExceptionWhenIntergalacticMissionIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
