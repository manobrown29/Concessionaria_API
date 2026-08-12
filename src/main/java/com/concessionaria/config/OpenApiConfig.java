package com.concessionaria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Concessionária Marcelo Gomes")
                        .description("API para controle de estoque de carros e cadastro de clientes, substituindo o controle por planilha.")
                        .version("1.0.0"));
    }
}