package com.zumin.dc.common.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Data
@Configuration
@EnableKnife4j
@EnableSwagger2
@ConfigurationProperties(prefix = "common.web.api")
public class SwaggerConfiguration {

  private String title;

  private String description;

  @Value("${common.core.projectPackage}")
  private String projectPackage;

  @Bean
  public Docket restApi() {
    //密码模式
    String passwordTokenUrl = "http://localhost:9999/dc-auth/oauth/token";
    ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
    OAuth oAuth = new OAuthBuilder().name("oauth2").grantTypes(Collections.singletonList(resourceOwnerPasswordCredentialsGrant)).build();
    //context
    //scope方位
    List<AuthorizationScope> scopes = new ArrayList<>();
    scopes.add(new AuthorizationScope("read", "read  resources"));
    scopes.add(new AuthorizationScope("write", "write resources"));
    scopes.add(new AuthorizationScope("reads", "read all resources"));
    scopes.add(new AuthorizationScope("writes", "write all resources"));

    SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
    SecurityContext securityContext = new SecurityContext(Lists.newArrayList(securityReference), PathSelectors.ant("/**"));
    //schemas
    List<SecurityScheme> securitySchemes = Lists.newArrayList(oAuth);
    //securyContext
    List<SecurityContext> securityContexts = Lists.newArrayList(securityContext);
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(projectPackage + ".controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(securityContexts)
        .securitySchemes(securitySchemes)
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title(title)
        .description("<div style='font-size:14px;color:red;'>" + description + "</div>")
        .contact(new Contact("yl", "https://github.com/zuminX", "1215287120@qq.com"))
        .license("Open Source")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
        .version("1.0.0")
        .build();
  }

}
