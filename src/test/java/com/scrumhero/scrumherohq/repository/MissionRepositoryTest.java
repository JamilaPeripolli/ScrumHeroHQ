package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.fixture.IntergalacticMissionFixture;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.Mission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.MissionFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MissionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MissionRepository repository;

    @Autowired
    private IntergalacticMissionRepository imRepository;

    @Test
    public void findByNameShouldReturnMission() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        repository.save(create());
        Optional<Mission> result = repository.findByName("Sprint 1");

        Mission expected = create();
        expected.setIntergalacticMission(intergalacticMission);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findByNameShouldReturnEmpty() {
        Optional<Mission> result = repository.findByName("Sprint 1");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findDuplicatedResourceShouldReturnMission() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        Mission mission = create();
        mission.setIntergalacticMission(new IntergalacticMission(1L));
        repository.save(mission);

        Optional<Mission> result = repository.findDuplicatedResource("Sprint 1", 2L, 1L);

        Mission expected = create();
        expected.setIntergalacticMission(intergalacticMission);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findDuplicatedResourceShouldReturnEmpty() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        Mission mission = create();
        mission.setIntergalacticMission(new IntergalacticMission(1L));
        repository.save(mission);

        Optional<Mission> result = repository.findDuplicatedResource("Sprint 1", 1L, 1L);

        assertThat(result.isPresent()).isFalse();
    }



}
