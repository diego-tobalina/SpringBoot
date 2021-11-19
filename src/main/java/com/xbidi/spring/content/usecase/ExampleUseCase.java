package com.xbidi.spring.content.usecase;

import com.xbidi.spring.content.shared.Constants;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Validated
@RestController
@AllArgsConstructor
@Api(tags = "UseCase", description = "Casos de uso")
@RequestMapping("/api/v0/use-case/")
public class ExampleUseCase {

  @Getter
  @Setter
  @ToString(callSuper = true)
  @ExampleUseCaseInputDTOConstraint
  static class ExampleUseCaseInputDTO {
    // TODO implement
  }

  @Getter
  @Setter
  @ToString()
  @AllArgsConstructor
  static class ExampleUseCaseOutputDTO {
    // TODO implement
  }

  @ResponseStatus(HttpStatus.NO_CONTENT) // TODO cambiar respuesta
  @GetMapping("/example") // TODO cambiar url
  @Transactional(rollbackFor = Exception.class)
  public ExampleUseCaseOutputDTO useCase(@RequestBody @Valid ExampleUseCaseInputDTO inputDTO) {
    // TODO implement
    return new ExampleUseCaseOutputDTO();
  }

  @Component
  @AllArgsConstructor
  static class ExampleUseCaseInputDTOValidator
      implements ConstraintValidator<ExampleUseCaseInputDTOConstraint, ExampleUseCaseInputDTO> {

    @Override
    public boolean isValid(
        ExampleUseCaseInputDTO inputDTO, ConstraintValidatorContext constraintValidatorContext) {
      // TODO implement
      return true;
    }

    @Override
    public void initialize(ExampleUseCaseInputDTOConstraint constraintAnnotation) {
      /* empty */
    }
  }

  @Constraint(validatedBy = ExampleUseCaseInputDTOValidator.class)
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @interface ExampleUseCaseInputDTOConstraint {
    String message() default Constants.ERROR_INPUT_DTO_FORMAT_NOT_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
  }
}
