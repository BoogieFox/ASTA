package altn72.TpFilRouge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * Configuration Web pour activer le support des méthodes HTTP PATCH, PUT, DELETE
 * dans les formulaires HTML Thymeleaf via le paramètre _method.
 */
@Configuration
public class WebConfig {

    /**
     * Active le HiddenHttpMethodFilter pour permettre l'utilisation de PATCH, PUT, DELETE
     * dans les formulaires HTML qui ne supportent nativement que GET et POST.
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
