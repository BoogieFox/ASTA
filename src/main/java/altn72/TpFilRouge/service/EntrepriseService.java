package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.repository.EntrepriseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
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
        return entrepriseRepository.save(entreprise);
    }

    @Transactional
    public void supprimerEntreprise(Integer id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        if (entreprise.isPresent()) {
            entrepriseRepository.deleteById(id);
        } else {
            throw new IllegalStateException("L'entreprise avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public Entreprise modifierEntreprise(Integer id, Entreprise entrepriseModified) {
        Entreprise entrepriseToModify = entrepriseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("L'entreprise avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(entrepriseModified, entrepriseToModify, "entrepriseId", "apprentis", "maitresApprentissage");
        return entrepriseRepository.save(entrepriseToModify);
    }
}
