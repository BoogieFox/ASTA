package altn72.TpFilRouge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ASTA - API de Suivi de Tutorat d'Apprentis")
                        .version("1.0.0")
                        .description("API pour gérer le suivi des apprentis, leurs dossiers annuels, missions, rapports et soutenances"));
    }
}
