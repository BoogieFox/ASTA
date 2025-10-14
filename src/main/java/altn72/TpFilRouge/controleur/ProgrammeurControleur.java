package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Programmeur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tpfilrouge")
public class ProgrammeurControleur {
    private final ProgrammeurService programmeurService;

    public ProgrammeurControleur(ProgrammeurService programmeurService) {
        this.programmeurService = programmeurService;
    }

    @GetMapping("/all")
    public String afficherInfosProgrammeurs(Model model){
        //return  programmeurService.getAllProgrameurs();
        List<Programmeur> listeProgrammeurs = programmeurService.getProgrammeurs();
        model.addAttribute("listeProgrammeurs", listeProgrammeurs);
        return "accueil";
    }

    @GetMapping("/programmeurs")
    public List<Programmeur> afficherProgrammeurs() {
        return programmeurService.getProgrammeurs();
    }

    @GetMapping("/unProgrammeur/{idProgrammeur}")
    public Optional<Programmeur> afficherUnProgrammeur(@PathVariable("idProgrammeur") Integer idProg) {
        return programmeurService.getUnProgrammeur(idProg);
    }

    @DeleteMapping("/supprimerProgrammeur/{idProgrammeur}")
//    @GetMapping("/supprimerProgrammeur/{idProgrammeur}")    @GetMapping fonctionne aussi :-)
    public void deleteProgrammeur(@PathVariable("idProgrammeur") Integer idProg) {
        programmeurService.supprimerProgrammeur(idProg);
    }

    @PostMapping("/ajouterProgrammeur")
    public void creerProgrammeur(@RequestBody Programmeur programmeur){
        programmeurService.ajouterProgrammeur(programmeur);
    }

    @PutMapping("modifier/{idProgrammeur}")
    public void modifierProgrammeur(@PathVariable Integer idProgrammeur,@RequestBody Programmeur programmeurModified ){
        programmeurService.modifierProgrammeur(idProgrammeur,programmeurModified);
    }
}