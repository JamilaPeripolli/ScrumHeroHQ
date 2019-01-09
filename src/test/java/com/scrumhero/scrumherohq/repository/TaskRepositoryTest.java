package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.fixture.IntergalacticMissionFixture;
import com.scrumhero.scrumherohq.fixture.LeagueFixture;
import com.scrumhero.scrumherohq.model.entity.EvolutionItem;
import com.scrumhero.scrumherohq.model.entity.IntergalacticMission;
import com.scrumhero.scrumherohq.model.entity.League;
import com.scrumhero.scrumherohq.model.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.TaskFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private IntergalacticMissionRepository imRepository;

    @Test
    public void findByTitleAndImIdShouldReturnEvolutionItem() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        repository.save(create());
        Optional<Task> result = repository.findByTitleAndIntergalacticMissionId("Task 1", 1L);

        Task expected = create();
        expected.setIntergalacticMission(intergalacticMission);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findByTitleAndImIdShouldReturnEmpty() {
        Optional<Task> result = repository.findByTitleAndIntergalacticMissionId("Task 1", 1L);

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findDuplicatedResourceShouldReturnEvolutionItem() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        Task task = create();
        task.setIntergalacticMission(new IntergalacticMission(1L));
        repository.save(task);

        Optional<Task> result = repository.findDuplicatedResource("Task 1", 2L, 1L);

        Task expected = create();
        expected.setIntergalacticMission(intergalacticMission);

        assertThat(result.get()).isEqualTo(expected);
    }

    @Test
    public void findDuplicatedResourceShouldReturnEmpty() {
        IntergalacticMission intergalacticMission = IntergalacticMissionFixture.create();
        imRepository.save(intergalacticMission);

        Task task = create();
        task.setIntergalacticMission(new IntergalacticMission(1L));
        repository.save(task);

        Optional<Task> result = repository.findDuplicatedResource("Task 1", 1L, 1L);

        assertThat(result.isPresent()).isFalse();
    }

}
