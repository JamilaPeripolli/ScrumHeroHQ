package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.MissionStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Mission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    private Boolean success;

    private String goal;

    @ManyToOne
    @JoinColumn(name = "intergalact_mission_id")
    private IntergalacticMission intergalacticMission;

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

    public IntergalacticMission getIntergalacticMission() {
        return intergalacticMission;
    }

    public void setIntergalacticMission(IntergalacticMission intergalacticMission) {
        this.intergalacticMission = intergalacticMission;
    }
}
