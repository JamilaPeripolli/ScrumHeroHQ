package com.scrumhero.scrumherohq.model.entity;

import com.scrumhero.scrumherohq.model.type.PlayerCategory;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name="user_id")
public class Player extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 100)
    private String nickname;

    private byte[] avatar;

    @Column(nullable = false)
    private Long score;

    @Column(nullable = false)
    private Long experience;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PlayerCategory category;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league; // TODO associativa entre player e league

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
