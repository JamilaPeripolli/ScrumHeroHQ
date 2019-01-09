package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.IntergalacticMissionDto;
import com.scrumhero.scrumherohq.model.dto.MissionDto;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.Mission;
import com.scrumhero.scrumherohq.model.type.MissionStatus;

public class MissionFixture {

    public static Mission create() {
        Mission mission = new Mission();
        mission.setId(1L);
        mission.setName("Sprint 1");
        mission.setStatus(MissionStatus.CREATED);
        mission.setIntergalacticMission(new IntergalacticMission(1L));

        return mission;
    }

    public static MissionDto createDto() {
        MissionDto mission = new MissionDto();
        mission.setId(1L);
        mission.setName("Sprint 1");
        mission.setStatus(MissionStatus.CREATED);
        mission.setIntergalacticMission(new IntergalacticMissionDto(1L));

        return mission;
    }
}
