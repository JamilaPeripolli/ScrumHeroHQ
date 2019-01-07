package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class EvolutionItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 150)
    private String title;

    private String description;

    private EvolutionItemStatus status;

    @NotNull
    private League league;

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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
