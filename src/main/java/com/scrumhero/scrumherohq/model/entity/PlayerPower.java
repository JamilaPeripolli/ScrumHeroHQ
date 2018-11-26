package com.scrumhero.scrumherohq.model.entity;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class PlayerPower implements Serializable {

    private static final long serialVersionUID = 1L;

    private Player player;

    private SuperPower superPower;

    private Long score;

    private Integer level;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SuperPower getSuperPower() {
        return superPower;
    }

    public void setSuperPower(SuperPower superPower) {
        this.superPower = superPower;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
