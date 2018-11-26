package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.IntergalacticMissionStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class IntergalacticMission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private IntergalacticMissionStatus status;

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

    public IntergalacticMissionStatus getStatus() {
        return status;
    }

    public void setStatus(IntergalacticMissionStatus status) {
        this.status = status;
    }
}
