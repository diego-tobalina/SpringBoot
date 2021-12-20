package com.diegotobalina.framework.provided.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static springfox.documentation.swagger.web.DocExpansion.NONE;
import static springfox.documentation.swagger.web.ModelRendering.EXAMPLE;
import static springfox.documentation.swagger.web.UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS;

/** @author diegotobalina created on 24/06/2020 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  private static final String BASE_PACKAGE = "com.diegotobalina.framework";
  private static final String HEADER = "header";
  private static final String TITLE = "Swagger";
  private static final String VERSION = "v0";
  private static final String MULTITENANT_HEADER = "X-Tenant-Id";
  private static final String AUTHORIZATION_HEADER = "Authorization";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
        .resourceChain(false);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
  }

  @Bean
  public Docket eDesignApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .securityContexts(List.of(securityContext()))
        .securitySchemes(
            List.of(
                new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, HEADER),
                new ApiKey(MULTITENANT_HEADER, MULTITENANT_HEADER, HEADER)))
        .ignoredParameterTypes(Principal.class)
        .ignoredParameterTypes(Pageable.class)
        .enable(true)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .directModelSubstitute(LocalDate.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .useDefaultResponseMessages(false)
        .enableUrlTemplating(false);
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    var authorizationScope = new AuthorizationScope("global", "accessEverything");
    var authorizationScopes = new AuthorizationScope[] {authorizationScope};
    return Arrays.asList(
        new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes),
        new SecurityReference(MULTITENANT_HEADER, authorizationScopes));
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .deepLinking(false)
        .displayOperationId(false)
        .defaultModelsExpandDepth(1)
        .defaultModelExpandDepth(1)
        .defaultModelRendering(EXAMPLE)
        .displayRequestDuration(true)
        .docExpansion(NONE)
        .filter(false)
        .maxDisplayedTags(0)
        .operationsSorter(OperationsSorter.ALPHA)
        .showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
        .supportedSubmitMethods(DEFAULT_SUBMIT_METHODS)
        .validatorUrl(null)
        .build();
  }

  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder().title(TITLE).version(VERSION).build();
  }
}
