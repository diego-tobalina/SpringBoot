package com.diegotobalina.framework.core.swagger;

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
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
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
import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.swagger.web.DocExpansion.NONE;
import static springfox.documentation.swagger.web.ModelRendering.EXAMPLE;
import static springfox.documentation.swagger.web.UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS;

/**
 * @author diegotobalina created on 24/06/2020
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {


    private static final String AUTHORIZATION = "Authorization";
    private static final String X_TENANT_ID = "X-Tenant-Id";


    @Bean
    public Docket eDesignApi() {

        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey(AUTHORIZATION, AUTHORIZATION, "header"));
        securitySchemes.add(new ApiKey(X_TENANT_ID, X_TENANT_ID, "header"));

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};

        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference(AUTHORIZATION, authorizationScopes));
        securityReferences.add(new SecurityReference(X_TENANT_ID, authorizationScopes));

        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(securityReferences).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("Swagger").version("0.0.1").build())
                .securityContexts(securityContexts)
                .securitySchemes(securitySchemes)
                .ignoredParameterTypes(Principal.class)
                .ignoredParameterTypes(Pageable.class)
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.diegotobalina.framework"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false);
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

}
