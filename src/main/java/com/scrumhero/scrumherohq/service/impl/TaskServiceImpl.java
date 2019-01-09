package com.scrumhero.scrumherohq.service.impl;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.TaskDto;
import com.scrumhero.scrumherohq.model.entity.Task;
import com.scrumhero.scrumherohq.model.type.TaskStatus;
import com.scrumhero.scrumherohq.repository.TaskRepository;
import com.scrumhero.scrumherohq.service.IntergalacticMissionService;
import com.scrumhero.scrumherohq.service.TaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    private TaskRepository repository;

    private IntergalacticMissionService imService;

    private ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository repository,
                           IntergalacticMissionService imService, ModelMapper modelMapper) {
        this.repository = repository;
        this.imService = imService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskDto save(TaskDto task) throws BadRequestException, ResourceNotFoundException {
        validateIntergalacticTask(task);
        checkDuplicatedResource(task);

        task.setStatus(TaskStatus.CREATED);

        Task persistedTask = repository
                .saveAndFlush(modelMapper.map(task, Task.class));

        LOGGER.debug("Task created: {}", task.getTitle());

        return modelMapper.map(persistedTask, TaskDto.class);
    }

    @Override
    public TaskDto update(TaskDto task) throws ResourceNotFoundException, BadRequestException {
        checkDuplicatedResource(task);

        TaskDto resource = getById(task.getId());
        task.setStatus(resource.getStatus());

        Task persistedTask = repository
                .saveAndFlush(modelMapper.map(task, Task.class));

        LOGGER.debug("Task updated: {}", task.getTitle());

        return modelMapper.map(persistedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAll() {
        Type type = new TypeToken<List<TaskDto>>() {}.getType();
        return modelMapper.map(repository.findAll(), type);
    }

    @Override
    public TaskDto getById(Long id) throws ResourceNotFoundException {
        Optional<Task> task = repository.findById(id);

        if(!task.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return modelMapper.map(task.get(), TaskDto.class);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        checkIfExists(id);

        repository.deleteById(id);

        LOGGER.debug("Task deleted, id: {}", id);
    }

    private void checkIfExists(Long id) throws ResourceNotFoundException {
        getById(id);
    }

    private void checkDuplicatedResource(TaskDto task) throws BadRequestException {
        Optional<Task> duplicatedResource;

        if (task.getId() == null) {
            duplicatedResource = repository.findByTitleAndIntergalacticMissionId(task.getTitle(), task.getIntergalacticMission().getId());
        } else {
            duplicatedResource = repository.findDuplicatedResource(task.getTitle(), task.getId(), task.getIntergalacticMission().getId());
        }

        if(duplicatedResource.isPresent()) {
            throw new BadRequestException("Invalid task, a resource with this title already exists.");
        }
    }

    private void validateIntergalacticTask(TaskDto task) throws BadRequestException, ResourceNotFoundException {
        if(task.getIntergalacticMission() == null
                || task.getIntergalacticMission().getId() == null) {
            throw new BadRequestException("To create a task it must be associated to an intergalactic mission. Please provide the required information.");
        }

        imService.getById(task.getIntergalacticMission().getId());
    }
}
