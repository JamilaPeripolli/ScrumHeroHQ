package com.scrumhero.scrumherohq.repository;

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
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void shouldReturnPlayer() {
        repository.save(createUser());
        Optional<User> result = repository.findByEmail("player@test.com");

        assertThat(result.get()).isEqualTo(createUser());
    }

    @Test
    public void shouldReturnEmpty() {
        Optional<User> result = repository.findByEmail("player@test.com");

        assertThat(result.isPresent()).isFalse();
    }

    private User createUser() {
        return new User(1L, "player", "player@test.com", "1234", AuthorityType.USER);
    }

}
