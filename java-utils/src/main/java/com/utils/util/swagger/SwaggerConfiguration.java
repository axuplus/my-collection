package com.utils.util.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
@PropertySource(value = "classpath:application.yml",encoding = "utf-8")
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Value(value = "${swagger.base_package}")
    private String base_package;

    @Value(value = "${swagger.title}")
    private String title;

    @Value(value = "${swagger.server_url}")
    private String server_url;

    @Value(value = "${swagger.version}")
    private String version;

    @Value(value = "${swagger.description}")
    private String description;

    @Bean
    public Docket createRestApi() {
        System.err.println(base_package);
        System.err.println(title);
        System.err.println(server_url);
        System.err.println(version);
        System.err.println(description);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
 //               .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage(base_package))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .termsOfServiceUrl(server_url)
                .version(version)
                .description(description)
                .build();
    }
}
