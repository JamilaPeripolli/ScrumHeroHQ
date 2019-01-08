package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.repository.SuperPowerRepository;
import com.scrumhero.scrumherohq.service.impl.SuperPowerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.scrumhero.scrumherohq.fixture.SuperPowerFixture.create;
import static com.scrumhero.scrumherohq.fixture.SuperPowerFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {SuperPowerServiceImpl.class})
public class SuperPowerServiceImplTest {

    @Autowired
    private SuperPowerService service;

    @MockBean
    private SuperPowerRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnSuperPower() throws BadRequestException {
        Mockito.when(repository.findByName("Java")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        SuperPowerDto superPower = createDto();
        superPower.setId(null);
        SuperPowerDto result = service.save(superPower);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenSuperPowerIsDuplicated() throws BadRequestException {
        Mockito.when(repository.findByName("Java")).thenReturn(Optional.of(create()));

        SuperPowerDto superPower = createDto();
        superPower.setId(null);

        service.save(superPower);
    }

    @Test
    public void updateShouldReturnSuperPower() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        SuperPowerDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenSuperPowerIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenSuperPowerIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findByNameWhereIdIsNotEquals("Java", 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void shouldReturnListOfSuperPower() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<SuperPowerDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void shouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<SuperPowerDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnSuperPower() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        SuperPowerDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenSuperPowerIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        SuperPowerDto result = service.getById(1L);
    }

    @Test
    public void shouldDeleteSuperPower() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenSuperPowerIsNotFoundDuringDeletion() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
