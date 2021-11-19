package com.xbidi.spring.content.example.application.usecase;

import com.xbidi.spring.content.example.application.ExampleService;
import com.xbidi.spring.content.example.domain.Example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateExampleUseCaseImplTest {

  private CreateExampleUseCase createExampleUseCase;
  private ExampleService exampleService;

  @BeforeEach
  public void setUp() {
    exampleService = Mockito.mock(ExampleService.class);
    createExampleUseCase = new CreateExampleUseCaseImpl(exampleService);
  }

  @Test
  void create() {
    when(exampleService.save(any()))
        .then(
            invocation -> {
              Example example = invocation.getArgument(0);
              example.setId(1L);
              return example;
            });

    Example example = createExampleUseCase.create(new Example());
    Assertions.assertEquals(1L, example.getId());
  }
}
