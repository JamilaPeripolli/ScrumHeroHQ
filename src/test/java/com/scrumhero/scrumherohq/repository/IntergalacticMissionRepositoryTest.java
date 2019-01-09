package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.IntergalacticMissionFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntergalacticMissionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IntergalacticMissionRepository repository;

    @Test
    public void findByNameShouldReturnIntergalacticMission() {
        repository.save(create());
        Optional<IntergalacticMission> result = repository.findByName("Project 1");

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameShouldReturnEmpty() {
        Optional<IntergalacticMission> result = repository.findByName("Project 1");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnIntergalacticMission() {
        repository.save(create());
        Optional<IntergalacticMission> result = repository.findByNameWhereIdIsNotEquals("Project 1", 2L);

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnEmpty() {
        repository.save(create());
        Optional<IntergalacticMission> result = repository.findByNameWhereIdIsNotEquals("Project 1", 1L);

        assertThat(result.isPresent()).isFalse();
    }



}
