package altn72.TpFilRouge.controleur;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Page d'authentification", description = "Endpoint de la vue Thymeleaf liée à la connexion")
@Controller
public class AuthController {

    @Operation(
            summary = "Page de connexion",
            description = "Affiche la page Thymeleaf du formulaire de connexion."
    )
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}

