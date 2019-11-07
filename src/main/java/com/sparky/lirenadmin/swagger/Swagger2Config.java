package com.sparky.lirenadmin.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket buildDocket() {
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder tokenPars = new ParameterBuilder();
        tokenPars.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPars.build());
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//调用下面apiInfo()方法
                .select()
                //Controller所在路径
                .apis(RequestHandlerSelectors.basePackage("com.sparky.lirenadmin.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);

    }

    public ApiInfo apiInfo() {
        return  new ApiInfoBuilder()
                .title("俪人管家Restful API")
                .description("俪人管家")
                .termsOfServiceUrl("http://127.0.0.1:8080")
                .contact("sparky team")
                .version("0.0.1")
                .build();

    }
}
