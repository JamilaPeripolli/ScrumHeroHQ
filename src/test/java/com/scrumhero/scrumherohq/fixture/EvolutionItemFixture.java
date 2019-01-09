package com.scrumhero.scrumherohq.fixture;

import com.scrumhero.scrumherohq.model.dto.EvolutionItemDto;
import com.scrumhero.scrumherohq.model.entity.EvolutionItem;
import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.type.EvolutionItemStatus;

public class EvolutionItemFixture {

    public static EvolutionItem create() {
        EvolutionItem evolutionItem = new EvolutionItem();
        evolutionItem.setId(1L);
        evolutionItem.setTitle("Item 1");
        evolutionItem.setStatus(EvolutionItemStatus.PENDING);
        evolutionItem.setLeague(new League(1L));

        return evolutionItem;
    }

    public static EvolutionItemDto createDto() {
        EvolutionItemDto evolutionItem = new EvolutionItemDto();
        evolutionItem.setId(1L);
        evolutionItem.setTitle("Item 1");
        evolutionItem.setStatus(EvolutionItemStatus.PENDING);
        evolutionItem.setLeague(new League(1L));

        return evolutionItem;
    }
}
