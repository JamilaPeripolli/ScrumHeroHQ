package com.scrumhero.scrumherohq.repository;

import com.scrumhero.scrumherohq.model.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.UserFixture.create;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void findByEmailShouldReturnPlayer() {
        repository.save(create());
        Optional<User> result = repository.findByEmail("player@mail.com");

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByEmailShouldReturnEmpty() {
        Optional<User> result = repository.findByEmail("player@mail.com");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findByEmailWhereIdIsNotEqualsShouldReturnUser() {
        repository.save(create());
        Optional<User> result = repository.findByEmailWhereIdIsNotEquals("player@mail.com", 2L);

        assertThat(result.get()).isEqualTo(create());
    }

    @Test
    public void findByEmailWhereIdIsNotEqualsShouldReturnEmpty() {
        repository.save(create());
        Optional<User> result = repository.findByEmailWhereIdIsNotEquals("player@mail.com", 1L);

        assertThat(result.isPresent()).isFalse();
    }

}
