/* Autogenerated file. Do not edit manually. */

package com.xbidi.spring.content.example.application.usecase;

import com.xbidi.spring.content.example.application.ExampleService;
import com.xbidi.spring.content.example.domain.Example;
import com.xbidi.spring.content.shared.anotation.UseCase;

@UseCase
public class CreateExampleUseCaseImpl implements CreateExampleUseCase {

  private final ExampleService exampleService;

  public CreateExampleUseCaseImpl(ExampleService exampleService) {
    this.exampleService = exampleService;
  }

  @Override
  public Example create(Example example) {
    return exampleService.save(example);
  }
}