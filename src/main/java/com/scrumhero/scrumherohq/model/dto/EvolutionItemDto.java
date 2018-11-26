package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

import java.io.Serializable;

public class EvolutionItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String description;

    private EvolutionItemStatus status;

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

    public EvolutionItemStatus getStatus() {
        return status;
    }

    public void setStatus(EvolutionItemStatus status) {
        this.status = status;
    }
}
