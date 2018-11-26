package com.scrumhero.scrumherohq.model.dto;

import java.io.Serializable;

public class LeagueDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private byte[] avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
