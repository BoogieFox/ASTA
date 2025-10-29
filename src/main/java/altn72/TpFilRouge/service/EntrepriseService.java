package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.EntrepriseDejaExistanteException;
import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.repository.EntrepriseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des entreprises.
 * Gère la logique métier liée aux entreprises : création, consultation et validation.
 */
@Service
public class EntrepriseService {
    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    /**
     * Récupère toutes les entreprises.
     * 
     * @return la liste de toutes les entreprises
     */
    public List<Entreprise> getEntreprises() {
        return entrepriseRepository.findAll();
    }

    /**
     * Récupère une entreprise par son identifiant.
     * 
     * @param id l'ID de l'entreprise
     * @return un Optional contenant l'entreprise si elle existe, Optional.empty() sinon
     */
    public Optional<Entreprise> getUneEntreprise(Integer id) {
        return entrepriseRepository.findById(id);
    }

    /**
     * Ajoute une nouvelle entreprise dans le système.
     * Vérifie que la raison sociale n'existe pas déjà.
     * 
     * @param entreprise l'entreprise à créer
     * @return l'entreprise créée avec son ID généré
     * @throws EntrepriseDejaExistanteException si la raison sociale existe déjà
     */
    @Transactional
    public Entreprise ajouterEntreprise(Entreprise entreprise) {
        // Check si raison sociale existe déjà
        if (entrepriseRepository.existsByRaisonSociale(entreprise.getRaisonSociale())) {
            throw new EntrepriseDejaExistanteException(entreprise.getRaisonSociale());
        }
        return entrepriseRepository.save(entreprise);
    }

}
