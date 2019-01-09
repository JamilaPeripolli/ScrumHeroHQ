package com.scrumhero.scrumherohq.service;

import com.scrumhero.scrumherohq.config.ModelMapperConfig;
import com.scrumhero.scrumherohq.exception.BadRequestException;
import com.scrumhero.scrumherohq.exception.ResourceNotFoundException;
import com.scrumhero.scrumherohq.model.dto.EvolutionItemDto;
import com.scrumhero.scrumherohq.repository.EvolutionItemRepository;
import com.scrumhero.scrumherohq.service.impl.EvolutionItemServiceImpl;
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

import static com.scrumhero.scrumherohq.fixture.EvolutionItemFixture.create;
import static com.scrumhero.scrumherohq.fixture.EvolutionItemFixture.createDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ModelMapperConfig.class})
@SpringBootTest(classes = {EvolutionItemServiceImpl.class})
public class EvolutionItemServiceImplTest {

    @Autowired
    private EvolutionItemService service;

    @MockBean
    private EvolutionItemRepository repository;

    @MockBean
    private LeagueService leagueService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveShouldReturnEvolutionItem() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.findByTitleAndLeagueId("Item 1", 1L)).thenReturn(Optional.empty());
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(create());

        EvolutionItemDto evolutionItem = createDto();
        evolutionItem.setId(null);
        evolutionItem.setStatus(null);

        EvolutionItemDto result = service.save(evolutionItem);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenEvolutionItemIsDuplicated() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.findByTitleAndLeagueId("Item 1", 1L)).thenReturn(Optional.of(create()));

        EvolutionItemDto evolutionItem = createDto();
        evolutionItem.setId(null);
        evolutionItem.setStatus(null);

        service.save(evolutionItem);
    }

    @Test(expected = BadRequestException.class)
    public void saveShouldThrowExceptionWhenLeagueIsMissing() throws BadRequestException, ResourceNotFoundException {
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(Optional.of(create()));

        EvolutionItemDto evolutionItem = createDto();
        evolutionItem.setId(null);
        evolutionItem.setStatus(null);
        evolutionItem.setLeague(null);

        service.save(evolutionItem);
    }

    @Test
    public void updateShouldReturnEvolutionItem() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        EvolutionItemDto result = service.update(createDto());

        assertThat(result).isEqualTo(createDto());
    }

    @Test
    public void updateShouldNotOverwriteAllFields() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.saveAndFlush(create())).thenReturn(create());

        EvolutionItemDto dto = createDto();
        dto.setStatus(null);

        EvolutionItemDto result = service.update(dto);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateShouldThrowExceptionWhenEvolutionItemIsNotFound() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.update(createDto());
    }

    @Test(expected = BadRequestException.class)
    public void updateShouldThrowExceptionWhenEvolutionItemIsDuplicated() throws ResourceNotFoundException, BadRequestException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));
        Mockito.when(repository.findDuplicatedResource("Item 1", 1L, 1L))
                .thenReturn(Optional.of(create()));

        service.update(createDto());
    }

    @Test
    public void getAllShouldReturnListOfEvolutionItem() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(create()));

        List<EvolutionItemDto> result = service.getAll();

        assertThat(result).containsExactly(createDto());
    }

    @Test
    public void getAllShouldReturnEmptyList() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());

        List<EvolutionItemDto> result = service.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void getByIdShouldReturnEvolutionItem() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(create()));

        EvolutionItemDto result = service.getById(1L);

        assertThat(result).isEqualTo(createDto());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getByIdShouldThrowExceptionWhenEvolutionItemIsNotFound() throws ResourceNotFoundException {
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
    public void deleteShouldThrowExceptionWhenEvolutionItemIsNotFound() throws ResourceNotFoundException {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        service.delete(1L);
    }

}
