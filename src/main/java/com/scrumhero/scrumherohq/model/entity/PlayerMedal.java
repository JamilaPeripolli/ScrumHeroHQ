package com.scrumhero.scrumherohq.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class PlayerMedal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    private Player player;

    @Id
    @ManyToOne
    private Medal medal;

    @Id
    @ManyToOne
    private IntergalacticMission intergalacticMission;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Medal getMedal() {
        return medal;
    }

    public void setMedal(Medal medal) {
        this.medal = medal;
    }

    public IntergalacticMission getIntergalacticMission() {
        return intergalacticMission;
    }

    public void setIntergalacticMission(IntergalacticMission intergalacticMission) {
        this.intergalacticMission = intergalacticMission;
    }
}
