package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.MissionStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class IntergalacticMissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private MissionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
