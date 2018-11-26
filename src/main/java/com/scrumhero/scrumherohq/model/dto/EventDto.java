package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class EventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private String notes;

    private EventType type;

    private List<PlayerDto> attendees;

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

    public List<PlayerDto> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<PlayerDto> attendees) {
        this.attendees = attendees;
    }
}
