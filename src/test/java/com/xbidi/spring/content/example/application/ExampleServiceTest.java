package com.xbidi.spring.content.example.application;

import com.xbidi.spring.content.example.domain.Example;
import com.xbidi.spring.content.example.infrastructure.repository.ExampleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExampleServiceTest {

  private ExampleService exampleService;
  private ExampleRepository exampleRepository;

  @BeforeEach
  public void setUp() {
    exampleRepository = Mockito.mock(ExampleRepository.class);
    exampleService = new ExampleServiceImpl(exampleRepository);
  }

  @Test
  void findById_ok() {
    Example mockExample = new Example();
    mockExample.setId(1L);
    Optional<Example> mockOptional = Optional.of(mockExample);
    Mockito.when(exampleRepository.findById(1L)).thenReturn(mockOptional);

    Example example = exampleService.findById(1L);
    Assertions.assertEquals(1L, example.getId());
    Assertions.assertEquals(mockExample, example);
  }

  @Test
  void findById_ko_EntityNotFoundException() {
    Mockito.when(exampleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    Assertions.assertThrows(EntityNotFoundException.class, () -> exampleService.findById(0L));
  }

  @Test
  void findByIdOrNull_ok() {
    Example mockExample = new Example();
    mockExample.setId(1L);
    Optional<Example> mockOptional = Optional.of(mockExample);
    Mockito.when(exampleRepository.findById(1L)).thenReturn(mockOptional);

    Example example = exampleService.findById(1L);
    Assertions.assertEquals(1L, example.getId());
    Assertions.assertEquals(mockExample, example);
  }

  @Test
  void findByIdOrNull_ok_null() {
    Mockito.when(exampleRepository.findById(1L)).thenReturn(Optional.empty());
    Example example = exampleService.findByIdOrNull(1L);
    Assertions.assertNull(example);
  }

  @Test
  void findAllByIds_ok() {
    List<Long> mockIds = List.of(1L, 2L, 3L);
    List<Example> mockExamples = new ArrayList<>();
    mockExamples.add(Example.builder().id(1L).build());
    mockExamples.add(Example.builder().id(2L).build());
    mockExamples.add(Example.builder().id(3L).build());
    Mockito.when(exampleRepository.findAllById(mockIds)).thenReturn(mockExamples);

    List<Example> examples = exampleService.findAllByIds(mockIds);
    Assertions.assertEquals(3, examples.size());
    Assertions.assertEquals(mockExamples, examples);
  }

  @Test
  void findAllByIds_ok_empty() {
    List<Long> mockIds = new ArrayList<>();
    List<Example> mockExamples = new ArrayList<>();
    Mockito.when(exampleRepository.findAllById(mockIds)).thenReturn(mockExamples);

    List<Example> examples = exampleService.findAllByIds(mockIds);
    Assertions.assertEquals(0, examples.size());
    Assertions.assertEquals(new ArrayList<>(), examples);
  }

  @Test
  void findAllByIds_ko_IllegalArgumentException_null() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> exampleService.findAllByIds(null));
  }

  @Test
  void save_ok() {
    when(exampleRepository.save(any()))
        .then(
            invocation -> {
              Example example = invocation.getArgument(0);
              example.setId(1L);
              return example;
            });

    Example example = new Example();
    Example savedExample = exampleService.save(example);
    Assertions.assertEquals(1L, savedExample.getId());
  }

  @Test
  void save_ko_IllegalArgumentException_null() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> exampleService.save(null));
  }

  @Test
  void saveAll() {
    when(exampleRepository.saveAll(any()))
        .then(
            invocation -> {
              List<Example> examples = invocation.getArgument(0);
              for (int i = 0; i < examples.size(); i++) {
                Example example = examples.get(i);
                example.setId((long) i);
              }
              return examples;
            });

    List<Example> exampleList = List.of(new Example(), new Example(), new Example());
    List<Example> savedExampleList = exampleService.saveAll(exampleList);
    for (int i = 0; i < savedExampleList.size(); i++) {
      Example example = savedExampleList.get(i);
      Assertions.assertEquals(i, example.getId());
    }
  }

  @Test
  void saveAll_ko_IllegalArgumentException_null() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> exampleService.saveAll(null));
  }

  @Test
  void deleteById_ok() {
    exampleService.deleteById(1L);
    Mockito.verify(exampleRepository, Mockito.times(1)).deleteById(1L);
  }

  @Test
  void deleteAllById_ok() {
    List<Long> ids = List.of(1L, 2L, 3L);
    exampleService.deleteAllById(ids);
    Mockito.verify(exampleRepository, Mockito.times(1)).deleteAllById(List.of(1L, 2L, 3L));
  }

  @Test
  void deleteAllById_ko_IllegalArgumentException_null() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> exampleService.deleteAllById(null));
  }
}
