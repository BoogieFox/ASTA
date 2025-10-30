package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Soutenance;
import altn72.TpFilRouge.modele.dto.CreerSoutenanceDto;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import altn72.TpFilRouge.modele.repository.SoutenanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des soutenances.
 * Gère la logique métier liée aux soutenances : création, modification, suppression et validation.
 * Un dossier annuel ne peut contenir qu'une seule soutenance.
 */
@Service
public class SoutenanceService {
    private final SoutenanceRepository soutenanceRepository;
    private final DossierAnnuelRepository dossierAnnuelRepository;

    public SoutenanceService(SoutenanceRepository soutenanceRepository, DossierAnnuelRepository dossierAnnuelRepository) {
        this.soutenanceRepository = soutenanceRepository;
        this.dossierAnnuelRepository = dossierAnnuelRepository;
    }

    /**
     * Ajoute une nouvelle soutenance à un dossier annuel.
     * Vérifie qu'aucune soutenance n'existe déjà pour ce dossier.
     *
     * @param dto les données de la soutenance à créer
     * @throws RessourceIntrouvableException si le dossier annuel n'existe pas
     * @throws RuntimeException              si une soutenance existe déjà pour ce dossier
     */
    @Transactional
    public void ajouterSoutenance(CreerSoutenanceDto dto) {
        // Récupérer le dossier annuel
        DossierAnnuel dossierAnnuel = dossierAnnuelRepository.findById(dto.getDossierAnnuelId())
                .orElseThrow(() -> new RessourceIntrouvableException("DossierAnnuel", dto.getDossierAnnuelId()));

        // Vérifier si une soutenance existe déjà pour ce dossier
        if (dossierAnnuel.getSoutenance() != null) {
            throw new RuntimeException("Une soutenance existe déjà pour ce dossier annuel");
        }

        // Créer la nouvelle soutenance
        Soutenance soutenance = new Soutenance();
        soutenance.setDate(dto.getDate());
        soutenance.setNoteFinale(dto.getNoteFinale());
        soutenance.setCommentaires(dto.getCommentaires());
        soutenance.setDossierAnnuel(dossierAnnuel);

        soutenanceRepository.save(soutenance);
    }

    /**
     * Modifie une soutenance existante.
     *
     * @param soutenanceId l'ID de la soutenance à modifier
     * @param dto          les nouvelles données de la soutenance
     * @throws RessourceIntrouvableException si la soutenance n'existe pas
     */
    @Transactional
    public void modifierSoutenance(Integer soutenanceId, CreerSoutenanceDto dto) {
        Soutenance soutenance = soutenanceRepository.findById(soutenanceId)
                .orElseThrow(() -> new RessourceIntrouvableException("Soutenance", soutenanceId));

        soutenance.setDate(dto.getDate());
        soutenance.setNoteFinale(dto.getNoteFinale());
        soutenance.setCommentaires(dto.getCommentaires());

        soutenanceRepository.save(soutenance);
    }

    /**
     * Supprime une soutenance.
     * Gère correctement la relation bidirectionnelle avec le dossier annuel.
     * 
     * @param soutenanceId l'ID de la soutenance à supprimer
     * @throws RessourceIntrouvableException si la soutenance n'existe pas
     */
    @Transactional
    public void supprimerSoutenance(Integer soutenanceId) {
        Soutenance soutenance = soutenanceRepository.findById(soutenanceId)
                .orElseThrow(() -> new RessourceIntrouvableException("Soutenance", soutenanceId));

        // Casser la relation bidirectionnelle avec le DossierAnnuel
        DossierAnnuel dossierAnnuel = soutenance.getDossierAnnuel();
        if (dossierAnnuel != null) {
            dossierAnnuel.setSoutenance(null);
            dossierAnnuelRepository.save(dossierAnnuel);
        }

        // Supprimer la soutenance
        soutenanceRepository.delete(soutenance);
    }
}

