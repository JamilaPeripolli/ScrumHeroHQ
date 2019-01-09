package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.MissionDto;
import com.scrumhero.scrumherohq.repository.MissionRepository;
import com.scrumhero.scrumherohq.service.impl.MissionServiceImpl;
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

import static com.scrumhero.scrumherohq.fixture.MissionFixture.create;
import static com.scrumhero.scrumherohq.fixture.MissionFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {MissionServiceImpl.class})
public class MissionServiceImplTest {

    @Autowired
    private MissionService service;

    @MockBean
    private MissionRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnMission() throws BadRequestException {
        Mockito.when(repository.findByName("Sprint 1")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        MissionDto mission = createDto();
        mission.setId(null);
        mission.setStatus(null);

        MissionDto result = service.save(mission);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenMissionIsDuplicated() throws BadRequestException {
        Mockito.when(repository.findByName("Sprint 1")).thenReturn(Optional.of(create()));

        MissionDto mission = createDto();
        mission.setId(null);
        mission.setStatus(null);

        service.save(mission);
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenIntergalacticMissionIsMissing() throws BadRequestException {
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(Optional.of(create()));

        MissionDto mission = createDto();
        mission.setId(null);
        mission.setStatus(null);
        mission.setIntergalacticMission(null);

        service.save(mission);
    }

    @Test
    public void updateShouldReturnMission() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        MissionDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test
    public void updateShouldNotOverwriteAllFields() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        MissionDto dto = createDto();
        dto.setEndDate(LocalDate.now());
        dto.setStartDate(LocalDate.now());
        dto.setStatus(null);

        MissionDto result = service.update(dto);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenMissionIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenMissionIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findDuplicatedResource("Sprint 1", 1L, 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfMission() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<MissionDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<MissionDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnMission() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        MissionDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenMissionIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.getById(1L);
    }

    @Test
    public void deleteShouldCallRepository() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteShouldThrowExceptionWhenMissionIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
