package com.server.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("X-AUTH-TOKEN") // name of header
                .modelRef(new ModelRef("string")).parameterType("header") // type - header
                .defaultValue("").required(false).build();

        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.server.attendance.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo()).globalOperationParameters(aParameters);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Backend for Hackathon").description("")
                .contact(new Contact("Mayuresh", "", "mmayuresh12@gmail.com")).version("1.1").build();
    }
}