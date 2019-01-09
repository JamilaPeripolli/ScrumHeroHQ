package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.MissionStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class MissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private MissionStatus status;

    private Boolean success;

    private String goal;

    private IntergalacticMissionDto intergalacticMission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public IntergalacticMissionDto getIntergalacticMission() {
        return intergalacticMission;
    }

    public void setIntergalacticMission(IntergalacticMissionDto intergalacticMission) {
        this.intergalacticMission = intergalacticMission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MissionDto that = (MissionDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                status == that.status &&
                Objects.equals(success, that.success) &&
                Objects.equals(goal, that.goal) &&
                Objects.equals(intergalacticMission, that.intergalacticMission);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, startDate, endDate, status, success, goal, intergalacticMission);
    }
}
