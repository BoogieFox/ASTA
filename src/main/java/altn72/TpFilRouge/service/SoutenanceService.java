package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Soutenance;
import altn72.TpFilRouge.modele.dto.CreerSoutenanceDto;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import altn72.TpFilRouge.modele.repository.SoutenanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SoutenanceService {
    private final SoutenanceRepository soutenanceRepository;
    private final DossierAnnuelRepository dossierAnnuelRepository;

    public SoutenanceService(SoutenanceRepository soutenanceRepository, DossierAnnuelRepository dossierAnnuelRepository) {
        this.soutenanceRepository = soutenanceRepository;
        this.dossierAnnuelRepository = dossierAnnuelRepository;
    }

    @Transactional
    public Soutenance ajouterSoutenance(CreerSoutenanceDto dto) {
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

        return soutenanceRepository.save(soutenance);
    }

    @Transactional
    public Soutenance modifierSoutenance(Integer soutenanceId, CreerSoutenanceDto dto) {
        Soutenance soutenance = soutenanceRepository.findById(soutenanceId)
                .orElseThrow(() -> new RessourceIntrouvableException("Soutenance", soutenanceId));

        soutenance.setDate(dto.getDate());
        soutenance.setNoteFinale(dto.getNoteFinale());
        soutenance.setCommentaires(dto.getCommentaires());

        return soutenanceRepository.save(soutenance);
    }

    @Transactional
    public void supprimerSoutenance(Integer soutenanceId) {
        if (!soutenanceRepository.existsById(soutenanceId)) {
            throw new RessourceIntrouvableException("Soutenance", soutenanceId);
        }
        soutenanceRepository.deleteById(soutenanceId);
    }
}

