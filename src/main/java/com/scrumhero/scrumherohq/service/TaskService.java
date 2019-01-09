package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto save(TaskDto task) throws BadRequestException, ResourceNotFoundException;

    TaskDto update(TaskDto task) throws ResourceNotFoundException, BadRequestException;

    List<TaskDto> getAll();

    TaskDto getById(Long id) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
