package com.scrumhero.scrumherohq.model.dto;

import java.io.Serializable;

public class PlayerPowerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private PlayerDto player;

    private SuperPowerDto superPower;

    private Long score;

    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public SuperPowerDto getSuperPower() {
        return superPower;
    }

    public void setSuperPower(SuperPowerDto superPower) {
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
