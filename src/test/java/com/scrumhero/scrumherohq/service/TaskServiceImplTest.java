package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.TaskDto;
import com.scrumhero.scrumherohq.repository.TaskRepository;
import com.scrumhero.scrumherohq.service.impl.TaskServiceImpl;
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

import static com.scrumhero.scrumherohq.fixture.TaskFixture.create;
import static com.scrumhero.scrumherohq.fixture.TaskFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {TaskServiceImpl.class})
public class TaskServiceImplTest {

    @Autowired
    private TaskService service;

    @MockBean
    private TaskRepository repository;

    @MockBean
    private IntergalacticMissionService imService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnTask() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.findByTitleAndIntergalacticMissionId("Task 1", 1L)).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        TaskDto task = createDto();
        task.setId(null);
        task.setStatus(null);

        TaskDto result = service.save(task);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenTaskIsDuplicated() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.findByTitleAndIntergalacticMissionId("Task 1", 1L)).thenReturn(Optional.of(create()));

        TaskDto task = createDto();
        task.setId(null);
        task.setStatus(null);

        service.save(task);
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenIntergalacticMissionIsMissing() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(Optional.of(create()));

        TaskDto task = createDto();
        task.setId(null);
        task.setStatus(null);
        task.setIntergalacticMission(null);

        service.save(task);
    }

    @Test
    public void updateShouldReturnTask() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        TaskDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test
    public void updateShouldNotOverwriteAllFields() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        TaskDto dto = createDto();
        dto.setStatus(null);

        TaskDto result = service.update(dto);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenTaskIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenTaskIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findDuplicatedResource("Task 1", 1L, 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfTask() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<TaskDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<TaskDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnTask() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        TaskDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenTaskIsNotFound() throws ResourceNotFoundException {
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
    public void deleteShouldThrowExceptionWhenTaskIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
