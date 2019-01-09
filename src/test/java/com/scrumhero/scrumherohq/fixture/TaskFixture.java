package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.TaskDto;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.Task;
import com.scrumhero.scrumherohq.model.type.TaskStatus;

public class TaskFixture {

    public static Task create() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.CREATED);
        task.setPriority(1);
        task.setIntergalacticMission(new IntergalacticMission(1L));

        return task;
    }

    public static TaskDto createDto() {
        TaskDto task = new TaskDto();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.CREATED);
        task.setPriority(1);
        task.setIntergalacticMission(new IntergalacticMission(1L));

        return task;
    }
}
