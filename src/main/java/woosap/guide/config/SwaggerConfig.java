package woosap.guide.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("API Doc")
            .description("보시고 피드백 부탁드려요\n 우선은 회원가입 후, auth요청을 하면 token을 주는데, 이후에 헤더에 Authorization : Bearer (공백 있습니다) + 발급받은 토큰으로 방 생성및 다른 요청들을 진행해주시면 됩니다.")
            .build();
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("People")
            .apiInfo(this.apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("woosap.guide.controller"))
            .paths(PathSelectors.ant("/api/**"))
            .build();
    }
}
