package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Rapport;
import altn72.TpFilRouge.modele.dto.CreerRapportDto;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import altn72.TpFilRouge.modele.repository.RapportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class RapportService {
    private final RapportRepository rapportRepository;
    private final DossierAnnuelRepository dossierAnnuelRepository;

    public RapportService(RapportRepository rapportRepository, DossierAnnuelRepository dossierAnnuelRepository) {
        this.rapportRepository = rapportRepository;
        this.dossierAnnuelRepository = dossierAnnuelRepository;
    }

    @Transactional
    public Rapport ajouterRapport(CreerRapportDto dto) {
        // Récupérer le dossier annuel
        DossierAnnuel dossierAnnuel = dossierAnnuelRepository.findById(dto.getDossierAnnuelId())
                .orElseThrow(() -> new RessourceIntrouvableException("DossierAnnuel", dto.getDossierAnnuelId()));

        // Vérifier si un rapport existe déjà pour ce dossier
        if (dossierAnnuel.getRapport() != null) {
            throw new RuntimeException("Un rapport existe déjà pour ce dossier annuel");
        }

        // Créer le nouveau rapport
        Rapport rapport = new Rapport();
        rapport.setSujet(dto.getSujet());
        rapport.setNoteFinale(dto.getNoteFinale());
        rapport.setCommentaires(dto.getCommentaires());
        rapport.setDossierAnnuel(dossierAnnuel);

        return rapportRepository.save(rapport);
    }

    @Transactional
    public Rapport modifierRapport(Integer rapportId, CreerRapportDto dto) {
        Rapport rapport = rapportRepository.findById(rapportId)
                .orElseThrow(() -> new RessourceIntrouvableException("Rapport", rapportId));

        rapport.setSujet(dto.getSujet());
        rapport.setNoteFinale(dto.getNoteFinale());
        rapport.setCommentaires(dto.getCommentaires());

        return rapportRepository.save(rapport);
    }

    @Transactional
    public void supprimerRapport(Integer rapportId) {
        Rapport rapport = rapportRepository.findById(rapportId)
                .orElseThrow(() -> new RessourceIntrouvableException("Rapport", rapportId));

        // Casser la relation bidirectionnelle avec le DossierAnnuel
        DossierAnnuel dossierAnnuel = rapport.getDossierAnnuel();
        if (dossierAnnuel != null) {
            dossierAnnuel.setRapport(null);
            dossierAnnuelRepository.save(dossierAnnuel);
        }

        // Supprimer le rapport
        rapportRepository.delete(rapport);
    }

}
