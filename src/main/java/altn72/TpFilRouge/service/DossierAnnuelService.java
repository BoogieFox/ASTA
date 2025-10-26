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

@Service
public class DossierAnnuelService {
    private final DossierAnnuelRepository dossierAnnuelRepository;
    private final ApprentiRepository apprentiRepository;

    public DossierAnnuelService(DossierAnnuelRepository dossierAnnuelRepository,
                                ApprentiRepository apprentiRepository) {
        this.dossierAnnuelRepository = dossierAnnuelRepository;
        this.apprentiRepository = apprentiRepository;
    }

    public Optional<DossierAnnuel> getUnDossierAnnuel(Integer dossierAnnuelId) {
        return dossierAnnuelRepository.findById(dossierAnnuelId);
    }

    public Optional<DossierAnnuel> getDossierCourant(Integer apprentiId) {
        return apprentiRepository.findById(apprentiId)
                .flatMap(apprenti -> dossierAnnuelRepository.findByApprentiAndPromotion(apprenti, apprenti.getPromotion()));
    }

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

