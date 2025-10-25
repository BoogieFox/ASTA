package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.EntrepriseDejaExistanteException;
import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.repository.EntrepriseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Entreprise> getEntreprises() {
        return entrepriseRepository.findAll();
    }

    public Optional<Entreprise> getUneEntreprise(Integer id) {
        return entrepriseRepository.findById(id);
    }

    @Transactional
    public Entreprise ajouterEntreprise(Entreprise entreprise) {
        // Check si raison sociale existe déjà
        if (entrepriseRepository.existsByRaisonSociale(entreprise.getRaisonSociale())) {
            throw new EntrepriseDejaExistanteException(entreprise.getRaisonSociale());
        }
        return entrepriseRepository.save(entreprise);
    }

}
