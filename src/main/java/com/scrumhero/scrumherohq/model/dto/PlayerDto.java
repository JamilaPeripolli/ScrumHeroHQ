package com.scrumhero.scrumherohq.model.dto;

import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.type.PlayerCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PlayerDto extends UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    private String nickname;

    private byte[] avatar;

    private Long score;

    private Long experience;

    private Integer level;

    @NotNull
    private PlayerCategory category;

    private League league;

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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
