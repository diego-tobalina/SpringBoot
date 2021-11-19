package com.xbidi.spring.content.example.application.usecase;

import com.xbidi.spring.content.example.application.ExampleService;
import com.xbidi.spring.exception.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteExampleUseCaseImplTest {

  private DeleteExampleUseCase deleteExampleUseCase;
  private FindExampleUseCase findExampleUseCase;
  private ExampleService exampleService;

  @BeforeEach
  public void setUp() {
    exampleService = Mockito.mock(ExampleService.class);
    findExampleUseCase = new FindExampleUseCaseImpl(exampleService);
    deleteExampleUseCase = new DeleteExampleUseCaseImpl(exampleService, findExampleUseCase);
  }

  @Test
  void delete_ok() throws EntityNotFoundException {
    deleteExampleUseCase.delete(1L);
    Mockito.verify(exampleService, Mockito.times(1)).deleteById(1L);
  }
}
