package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class EvolutionItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String title;

    private String description;

    private EvolutionItemStatus status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvolutionItemDto that = (EvolutionItemDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                status == that.status &&
                Objects.equals(league, that.league);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, description, status, league);
    }
}
