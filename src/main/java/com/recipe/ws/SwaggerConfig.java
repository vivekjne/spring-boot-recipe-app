package com.recipe.ws;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Lists;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				 .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build()
		          .apiInfo(apiInfo())
		          .securityContexts(Lists.newArrayList(securityContext()))
		          .securitySchemes(Arrays.asList(apiKey()));
		            
	}
	
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Recipe REST API Document")
	            .description("Rest api for recipe")
	            .termsOfServiceUrl("localhost")
	            .version("1.0")
	            .build();
	}
	
	 private SecurityContext securityContext() {
	        return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .forPaths(PathSelectors.regex("/.*"))
	            .build();
	    }
	 
	 List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Lists.newArrayList(
	            new SecurityReference("jwtToken", authorizationScopes));
	    }

	private ApiKey apiKey() {
	    return new ApiKey("jwtToken", "Authorization", "header");
	}
}

