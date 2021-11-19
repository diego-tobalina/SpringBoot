package com.xbidi.spring.content.example.application.usecase;

import com.xbidi.spring.content.example.application.ExampleService;
import com.xbidi.spring.content.example.domain.Example;
import com.xbidi.spring.exception.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateExampleUseCaseImplTest {

  private UpdateExampleUseCase updateExampleUseCase;
  private FindExampleUseCase findExampleUseCase;
  private ExampleService exampleService;

  @BeforeEach
  public void setUp() {
    exampleService = Mockito.mock(ExampleService.class);
    findExampleUseCase = new FindExampleUseCaseImpl(exampleService);
    updateExampleUseCase = new UpdateExampleUseCaseImpl(findExampleUseCase, exampleService);
  }

  @Test
  void update() throws EntityNotFoundException {
    when(exampleService.save(any())).then(invocation -> invocation.getArgument(0));
    when(exampleService.findById(Mockito.anyLong())).thenReturn(new Example());
    Example example = new Example();
    Example updatedExample = updateExampleUseCase.update(1L, example);
    Mockito.verify(exampleService, Mockito.times(1)).save(updatedExample);
  }
}
