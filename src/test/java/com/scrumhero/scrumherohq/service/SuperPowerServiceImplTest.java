package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.SuperPowerDto;
import com.scrumhero.scrumherohq.model.entity.SuperPower;
import com.scrumhero.scrumherohq.model.type.AuthorityType;
import com.scrumhero.scrumherohq.repository.SuperPowerRepository;
import com.scrumhero.scrumherohq.service.impl.SuperPowerServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
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
    public void shouldSave() throws BadRequestException {
        Mockito.when(repository.findByName("Java")).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(createTestSuperPower());

        SuperPowerDto result = service.save(createTestSuperPowerDto());

        assertThat(result).isEqualTo(createTestSuperPowerDto());
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowExceptionWhenSuperPowerIsDuplicated() throws BadRequestException {
        Mockito.when(repository.findByName("Java")).thenReturn(Optional.of(createTestSuperPower()));

        service.save(createTestSuperPowerDto());
    }

    @Test
    public void shouldUpdate() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(createTestSuperPower()));
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(createTestSuperPower());

        SuperPowerDto result = service.update(createTestSuperPowerDto());

        assertThat(result).isEqualTo(createTestSuperPowerDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenSuperPowerIsNotFoundDuringUpdate() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createTestSuperPowerDto());
    }

    @Test
    public void shouldReturnListOfSuperPower() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(createTestSuperPower()));

        List<SuperPowerDto> result = service.getAll();

        assertThat(result).containsExactly(createTestSuperPowerDto());
    }

    @Test
    public void shouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<SuperPowerDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnSuperPower() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(createTestSuperPower()));

        SuperPowerDto result = service.getById(1L);

        assertThat(result).isEqualTo(createTestSuperPowerDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenSuperPowerIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        SuperPowerDto result = service.getById(1L);
    }

    @Test
    public void shouldDeleteSuperPower() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(createTestSuperPower()));

        service.delete(1L);

        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenSuperPowerIsNotFoundDuringDeletion() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

    private SuperPowerDto createTestSuperPowerDto() {
        SuperPowerDto superPower = new SuperPowerDto();
        superPower.setId(1L);
        superPower.setName("Java");
        superPower.setDescription("The ability to write functional code in Java");

        return superPower;
    }

    private SuperPower createTestSuperPower() {
        SuperPower superPower = new SuperPower();
        superPower.setId(1L);
        superPower.setName("Java");
        superPower.setDescription("The ability to write functional code in Java");

        return superPower;
    }
}
