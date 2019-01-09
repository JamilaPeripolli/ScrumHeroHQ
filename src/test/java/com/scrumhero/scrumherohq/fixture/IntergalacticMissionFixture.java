package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.SuperPower;
import com.scrumhero.scrumherohq.model.type.MissionStatus;

public class IntergalacticMissionFixture {

    public static IntergalacticMission create() {
        IntergalacticMission intergalacticMission = new IntergalacticMission();
        intergalacticMission.setId(1L);
        intergalacticMission.setName("Project 1");
        intergalacticMission.setDescription("First software project");
        intergalacticMission.setStatus(MissionStatus.CREATED);

        return intergalacticMission;
    }

    public static IntergalacticMissionDto createDto() {
        IntergalacticMissionDto intergalacticMission = new IntergalacticMissionDto();
        intergalacticMission.setId(1L);
        intergalacticMission.setName("Project 1");
        intergalacticMission.setDescription("First software project");
        intergalacticMission.setStatus(MissionStatus.CREATED);

        return intergalacticMission;
    }
}
