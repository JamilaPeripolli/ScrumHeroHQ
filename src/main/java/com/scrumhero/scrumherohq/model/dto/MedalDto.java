package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.type.MedalCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MedalDto {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String title;

    private String description;

    @NotNull
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
