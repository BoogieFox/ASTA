package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.dto.CreerEntrepriseDto;
import altn72.TpFilRouge.service.EntrepriseService;
import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/entreprises")
public class EntrepriseControleur {

    private final ModelMapper modelMapper;
    private final EntrepriseService entrepriseService;

    public EntrepriseControleur(ModelMapper modelMapper, EntrepriseService entrepriseService) {
        this.modelMapper = modelMapper;
        this.entrepriseService = entrepriseService;
    }

    @PostMapping("/nouveau")
    public ResponseEntity<Entreprise> creerEntreprise(@Valid @RequestBody CreerEntrepriseDto dto) {
        Entreprise entreprise = modelMapper.map(dto, Entreprise.class);
        Entreprise created = entrepriseService.ajouterEntreprise(entreprise);
        URI location = URI.create("/api/entreprises/" + created.getEntrepriseId());
        return ResponseEntity.created(location).body(created);
    }
}