package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.Mission;
import com.scrumhero.scrumherohq.model.entity.Player;
import com.scrumhero.scrumherohq.model.entity.SuperPower;
import com.scrumhero.scrumherohq.model.type.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TaskDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 150)
    private String title;

    private String description;

    private String notes;

    private String acceptanceCriteria;

    @NotNull
    private Integer priority;

    private Integer estimate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private IntergalacticMission intergalacticMission;

    private Mission mission;

    private Player player;

    private SuperPower superPower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public IntergalacticMission getIntergalacticMission() {
        return intergalacticMission;
    }

    public void setIntergalacticMission(IntergalacticMission intergalacticMission) {
        this.intergalacticMission = intergalacticMission;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SuperPower getSuperPower() {
        return superPower;
    }

    public void setSuperPower(SuperPower superPower) {
        this.superPower = superPower;
    }
}
