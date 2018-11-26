package com.scrumhero.scrumherohq.model.dto;

import java.io.Serializable;

public class PlayerMedalDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private PlayerDto player;

    private MedalDto medal;

    private IntergalacticMissionDto intergalacticMission;

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public MedalDto getMedal() {
        return medal;
    }

    public void setMedal(MedalDto medal) {
        this.medal = medal;
    }

    public IntergalacticMissionDto getIntergalacticMission() {
        return intergalacticMission;
    }

    public void setIntergalacticMission(IntergalacticMissionDto intergalacticMission) {
        this.intergalacticMission = intergalacticMission;
    }
}
