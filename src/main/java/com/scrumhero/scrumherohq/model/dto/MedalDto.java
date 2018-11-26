package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.MedalCategory;

public class MedalDto {

    private Long id;

    private String title;

    private String description;

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
