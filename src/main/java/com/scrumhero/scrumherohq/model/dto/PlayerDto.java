package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.PlayerCategory;

import java.io.Serializable;

public class PlayerDto extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nickname;

    private byte[] avatar;

    private Long score;

    private Long experience;

    private Integer level;

    private PlayerCategory category;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public PlayerCategory getCategory() {
        return category;
    }

    public void setCategory(PlayerCategory category) {
        this.category = category;
    }
}
