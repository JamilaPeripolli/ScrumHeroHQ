package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.MedalCategory;

import javax.persistence.*;

@Entity
public class Medal {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private MedalCategory category;

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
