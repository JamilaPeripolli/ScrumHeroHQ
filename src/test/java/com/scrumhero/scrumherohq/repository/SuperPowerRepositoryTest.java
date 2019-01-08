package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.SuperPower;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SuperPowerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SuperPowerRepository repository;

    @Test
    public void shouldReturnSuperPower() {
        repository.save(createTestSuperPower());
        Optional<SuperPower> result = repository.findByName("Java");

        assertThat(result.get()).isEqualTo(createTestSuperPower());
    }

    @Test
    public void shouldReturnEmpty() {
        Optional<SuperPower> result = repository.findByName("Java");

        assertThat(result.isPresent()).isFalse();
    }

    private SuperPower createTestSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setId(1L);
        superPower.setName("Java");
        superPower.setDescription("The ability to write functional code in Java");

        return superPower;
    }

}
