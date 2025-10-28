package altn72.TpFilRouge.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Thymeleaf template login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // template dashboard.html
    }
}

