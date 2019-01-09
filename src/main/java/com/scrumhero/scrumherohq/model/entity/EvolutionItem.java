package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class EvolutionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvolutionItem that = (EvolutionItem) o;
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
