package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.fixture.UserFixture;
import com.scrumhero.scrumherohq.model.dto.UserDto;
import com.scrumhero.scrumherohq.model.entity.User;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.repository.UserRepository;
import com.scrumhero.scrumherohq.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.UserFixture.create;
import static com.scrumhero.scrumherohq.fixture.UserFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {UserServiceImpl.class})
public class UserServiceImplTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnUser() throws BadRequestException {
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn(Mockito.anyString());
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        UserDto userDto = createDto(AuthorityType.USER);
        userDto.setId(null);
        UserDto result = service.save(userDto);

        assertThat(result).isEqualTo(createDto(AuthorityType.USER));
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenUserIsDuplicated() throws BadRequestException {
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn(Mockito.anyString());
        Mockito.when(repository.findByEmail("player@mail.com")).thenReturn(Optional.of(new User()));

        UserDto userDto = createDto(AuthorityType.USER);
        userDto.setId(null);

        service.save(userDto);
    }

    @Test
    public void updateShouldReturnUser() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        UserDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenUserIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenUserIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserFixture.create()));
        Mockito.when(repository.findByEmailWhereIdIsNotEquals("player@mail.com", 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfUser() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<UserDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<UserDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnUser() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        UserDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenUserIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.getById(1L);
    }

    @Test
    public void deleteShouldCallRepository() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteShouldThrowExceptionWhenUserIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}