package altn72.TpFilRouge.controleur;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilControleur {

    @GetMapping("/")
    public String accueil() {
        return "redirect:/apprentis";
    }
}
