package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.LeagueDto;
import com.scrumhero.scrumherohq.model.entity.League;

public class LeagueFixture {

    public static League create() {
        League league = new League();
        league.setId(1L);
        league.setName("First Team");

        return league;
    }

    public static LeagueDto createDto() {
        LeagueDto league = new LeagueDto();
        league.setId(1L);
        league.setName("First Team");

        return league;
    }
}
