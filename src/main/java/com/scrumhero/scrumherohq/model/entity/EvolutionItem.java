package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class EvolutionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EvolutionItemStatus status;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
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
