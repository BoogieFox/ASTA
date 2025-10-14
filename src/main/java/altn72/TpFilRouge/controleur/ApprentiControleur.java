package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tpfilrouge")
public class ApprentiControleur {
    private final altn72.TpFilRouge.service.ApprentiService ApprentiService;

    public ApprentiControleur(ApprentiService ApprentiService) {
        this.ApprentiService = ApprentiService;
    }

    @GetMapping("/all")
    public String afficherInfosApprentis(Model model){
        //return  ApprentiService.getAllProgrameurs();
        List<Apprenti> listeApprentis = ApprentiService.getApprentis();
        model.addAttribute("listeApprentis", listeApprentis);
        return "accueil";
    }

    @GetMapping("/Apprentis")
    public List<Apprenti> afficherApprentis() {
        return ApprentiService.getApprentis();
    }

    @GetMapping("/unApprenti/{idApprenti}")
    public Optional<Apprenti> afficherUnApprenti(@PathVariable("idApprenti") Integer idProg) {
        return ApprentiService.getUnApprenti(idProg);
    }

    @DeleteMapping("/supprimerApprenti/{idApprenti}")
//    @GetMapping("/supprimerApprenti/{idApprenti}")    @GetMapping fonctionne aussi :-)
    public void deleteApprenti(@PathVariable("idApprenti") Integer idProg) {
        ApprentiService.supprimerApprenti(idProg);
    }

    @PostMapping("/ajouterApprenti")
    public void creerApprenti(@RequestBody Apprenti Apprenti){
        ApprentiService.ajouterApprenti(Apprenti);
    }

    @PutMapping("modifier/{idApprenti}")
    public void modifierApprenti(@PathVariable Integer idApprenti,@RequestBody Apprenti ApprentiModified ){
        ApprentiService.modifierApprenti(idApprenti,ApprentiModified);
    }
}