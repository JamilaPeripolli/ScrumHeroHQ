package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.League;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.LeagueFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeagueRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LeagueRepository repository;

    @Test
    public void findByNameShouldReturnLeague() {
        repository.save(create());
        Optional<League> result = repository.findByName("First Team");

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameShouldReturnEmpty() {
        Optional<League> result = repository.findByName("First Team");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnLeague() {
        repository.save(create());
        Optional<League> result = repository.findByNameWhereIdIsNotEquals("First Team", 2L);

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnEmpty() {
        repository.save(create());
        Optional<League> result = repository.findByNameWhereIdIsNotEquals("First Team", 1L);

        assertThat(result.isPresent()).isFalse();
    }

}
