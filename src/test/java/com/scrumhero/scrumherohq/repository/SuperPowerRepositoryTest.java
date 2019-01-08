package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.SuperPower;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.SuperPowerFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SuperPowerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SuperPowerRepository repository;

    @Test
    public void findByNameShouldReturnSuperPower() {
        repository.save(create());
        Optional<SuperPower> result = repository.findByName("Java");

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameShouldReturnEmpty() {
        Optional<SuperPower> result = repository.findByName("Java");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnSuperPower() {
        repository.save(create());
        Optional<SuperPower> result = repository.findByNameWhereIdIsNotEquals("Java", 2L);

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByNameWhereIdIsNotEqualsShouldReturnEmpty() {
        repository.save(create());
        Optional<SuperPower> result = repository.findByNameWhereIdIsNotEquals("Java", 1L);

        assertThat(result.isPresent()).isFalse();
    }

}
