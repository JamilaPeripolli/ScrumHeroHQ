package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.MedalCategory;

import javax.persistence.*;

@Entity
public class Medal {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MedalCategory category;

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

    public MedalCategory getCategory() {
        return category;
    }

    public void setCategory(MedalCategory category) {
        this.category = category;
    }
}
