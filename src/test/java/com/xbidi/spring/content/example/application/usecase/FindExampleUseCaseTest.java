package com.xbidi.spring.content.example.application.usecase;

import com.xbidi.spring.content.example.application.ExampleService;
import com.xbidi.spring.content.example.domain.Example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;

class FindExampleUseCaseTest {

  private FindExampleUseCase findExampleUseCaseImpl;
  private ExampleService exampleService;

  @BeforeEach
  public void setUp() {
    exampleService = Mockito.mock(ExampleService.class);
    findExampleUseCaseImpl = new FindExampleUseCaseImpl(exampleService);
  }

  @Test
  void findById_ok() {
    Example mockExample = new Example();
    mockExample.setId(1L);
    Mockito.when(exampleService.findById(1L)).thenReturn(mockExample);

    Example example = findExampleUseCaseImpl.findById(1L);
    Assertions.assertEquals(mockExample, example);
  }

  @Test
  void findById_ko() {
    Mockito.when(exampleService.findById(1L))
        .thenThrow(new EntityNotFoundException(String.format("example not found with id: %d", 1L)));
    Assertions.assertThrows(
        EntityNotFoundException.class, () -> findExampleUseCaseImpl.findById(1L));
  }

  @Test
  void findByIdOrNull_ok() {
    Example mockExample = new Example();
    mockExample.setId(1L);
    Mockito.when(exampleService.findByIdOrNull(1L)).thenReturn(mockExample);

    Example example = findExampleUseCaseImpl.findByIdOrNull(1L);
    Assertions.assertEquals(mockExample, example);
  }

  @Test
  void findByIdOrNull_ok_null() {
    Mockito.when(exampleService.findByIdOrNull(1L)).thenReturn(null);
    Example example = findExampleUseCaseImpl.findByIdOrNull(1L);
    Assertions.assertNull(example);
  }
}
