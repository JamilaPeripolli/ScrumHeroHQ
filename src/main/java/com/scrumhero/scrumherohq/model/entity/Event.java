package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.EventType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EventType type;

    @ManyToMany
    @JoinTable(name = "event_attendees",
            joinColumns = { @JoinColumn(name = "event_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<Player> attendees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<Player> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Player> attendees) {
        this.attendees = attendees;
    }
}
