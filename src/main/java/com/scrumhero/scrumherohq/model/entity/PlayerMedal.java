package com.scrumhero.scrumherohq.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PlayerMedal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "medal_id", nullable = false)
    private Medal medal;

    @ManyToOne
    @JoinColumn(name = "intergalactic_mission_id", nullable = false)
    private IntergalacticMission intergalacticMission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
