package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.fixture.LeagueFixture;
import com.scrumhero.scrumherohq.model.entity.EvolutionItem;
import com.scrumhero.scrumherohq.model.entity.League;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.EvolutionItemFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EvolutionItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EvolutionItemRepository repository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    public void findByTitleAndLeagueIdShouldReturnEvolutionItem() {
        League league = LeagueFixture.create();
        leagueRepository.save(league);

        repository.save(create());
        Optional<EvolutionItem> result = repository.findByTitleAndLeagueId("Item 1", 1L);

        EvolutionItem expected = create();
        expected.setLeague(league);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findByTitleAndLeagueIdShouldReturnEmpty() {
        Optional<EvolutionItem> result = repository.findByTitleAndLeagueId("Item 1", 1L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findDuplicatedResourceShouldReturnEvolutionItem() {
        League league = LeagueFixture.create();
        leagueRepository.save(league);

        EvolutionItem evolutionItem = create();
        evolutionItem.setLeague(new League(1L));
        repository.save(evolutionItem);

        Optional<EvolutionItem> result = repository.findDuplicatedResource("Item 1", 2L, 1L);

        EvolutionItem expected = create();
        expected.setLeague(league);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findDuplicatedResourceShouldReturnEmpty() {
        League league = LeagueFixture.create();
        leagueRepository.save(league);

        EvolutionItem evolutionItem = create();
        evolutionItem.setLeague(new League(1L));
        repository.save(evolutionItem);

        Optional<EvolutionItem> result = repository.findDuplicatedResource("Item 1", 1L, 1L);

        assertThat(result.isPresent()).isFalse();
    }

}
