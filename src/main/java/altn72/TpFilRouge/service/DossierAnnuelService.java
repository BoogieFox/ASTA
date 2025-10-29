package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Promotion;
import altn72.TpFilRouge.modele.repository.ApprentiRepository;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service de gestion des dossiers annuels.
 * Gère la logique métier liée aux dossiers annuels : création, consultation et récupération du dossier courant d'un apprenti.
 */
@Service
public class DossierAnnuelService {
    private final DossierAnnuelRepository dossierAnnuelRepository;
    private final ApprentiRepository apprentiRepository;

    public DossierAnnuelService(DossierAnnuelRepository dossierAnnuelRepository,
                                ApprentiRepository apprentiRepository) {
        this.dossierAnnuelRepository = dossierAnnuelRepository;
        this.apprentiRepository = apprentiRepository;
    }

    /**
     * Récupère un dossier annuel par son identifiant.
     * 
     * @param dossierAnnuelId l'ID du dossier annuel
     * @return un Optional contenant le dossier annuel s'il existe, Optional.empty() sinon
     */
    public Optional<DossierAnnuel> getUnDossierAnnuel(Integer dossierAnnuelId) {
        return dossierAnnuelRepository.findById(dossierAnnuelId);
    }

    /**
     * Récupère le dossier annuel courant d'un apprenti (correspondant à sa promotion actuelle).
     * 
     * @param apprentiId l'ID de l'apprenti
     * @return un Optional contenant le dossier courant s'il existe, Optional.empty() sinon
     */
    public Optional<DossierAnnuel> getDossierCourant(Integer apprentiId) {
        return apprentiRepository.findById(apprentiId)
                .flatMap(apprenti -> dossierAnnuelRepository.findByApprentiAndPromotion(apprenti, apprenti.getPromotion()));
    }

    /**
     * Crée un nouveau dossier annuel pour la promotion actuelle d'un apprenti.
     * Vérifie qu'aucun dossier n'existe déjà pour cette promotion.
     * 
     * @param apprentiId l'ID de l'apprenti
     * @return le dossier annuel créé
     * @throws RessourceIntrouvableException si l'apprenti n'existe pas
     * @throws IllegalStateException si un dossier existe déjà pour cette promotion
     */
    @Transactional
    public DossierAnnuel creerDossierCourant(Integer apprentiId) {
        Apprenti apprenti = apprentiRepository.findById(apprentiId)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", apprentiId));

        Promotion promotion = apprenti.getPromotion();
        if (dossierAnnuelRepository.existsByApprentiAndPromotion(apprenti, promotion)) {
            throw new IllegalStateException("Un dossier annuel existe déjà pour la promotion " + promotion.getLabel());
        }

        DossierAnnuel dossierAnnuel = new DossierAnnuel(apprenti, promotion);
        apprenti.ajouterDossierAnnuel(dossierAnnuel);

        return dossierAnnuelRepository.save(dossierAnnuel);
    }

}

