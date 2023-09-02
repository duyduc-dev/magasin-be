package com.ms.magasinbe.configs.spingdoc;

import com.ms.magasinbe.common.utils.ApplicationValueConfigure;
import com.ms.magasinbe.common.utils.Constant;
import com.ms.magasinbe.controllers.ApiPath;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SpringDocOpenApiConfig {
  @Autowired
  ApplicationValueConfigure applicationValueConfigure;


  @Bean
  public GroupedOpenApi defaultApi() {
    return GroupedOpenApi.builder()
            .group("Default")
            .pathsToMatch(ApiPath.BASE_API_URL + "/**")
            .build();
  }


  @Bean
  public OpenAPI applicationOpenAPI() {
    final String securitySchemeName = Constant.HEADER_TOKEN;
    final String apiTitle = String.format("%s API", StringUtils.capitalize(applicationValueConfigure.APP_NAME));
    return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(
                    new Components()
                            .addSecuritySchemes(
                                    securitySchemeName,
                                    new SecurityScheme()
                                            .name(securitySchemeName)
                                            .type(SecurityScheme.Type.APIKEY)
                                            .in(SecurityScheme.In.HEADER)))
            .info(
                    new Info()
                            .title(apiTitle)
                            .version(applicationValueConfigure.APP_API_VERSION)
                            .license(new License().name("Dang Duy Duc").url("https://www.facebook.com/dangduyduc1908")));
  }
}
