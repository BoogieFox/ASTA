package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Visite;
import altn72.TpFilRouge.modele.dto.CreerVisiteDto;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import altn72.TpFilRouge.modele.repository.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des visites.
 * Gère la logique métier liée aux visites : création, modification, suppression et validation.
 * Un dossier annuel ne peut contenir qu'une seule visite.
 */
@Service
public class VisiteService {
    private final VisiteRepository visiteRepository;
    private final DossierAnnuelRepository dossierAnnuelRepository;

    public VisiteService(VisiteRepository visiteRepository, DossierAnnuelRepository dossierAnnuelRepository) {
        this.visiteRepository = visiteRepository;
        this.dossierAnnuelRepository = dossierAnnuelRepository;
    }

    /**
     * Ajoute une nouvelle visite à un dossier annuel.
     * Vérifie qu'aucune visite n'existe déjà pour ce dossier.
     * 
     * @param dto les données de la visite à créer
     * @return la visite créée avec son ID généré
     * @throws RessourceIntrouvableException si le dossier annuel n'existe pas
     * @throws RuntimeException si une visite existe déjà pour ce dossier
     */
    @Transactional
    public Visite ajouterVisite(CreerVisiteDto dto) {
        // Récupérer le dossier annuel
        DossierAnnuel dossierAnnuel = dossierAnnuelRepository.findById(dto.getDossierAnnuelId())
                .orElseThrow(() -> new RessourceIntrouvableException("DossierAnnuel", dto.getDossierAnnuelId()));

        // Vérifier si une visite existe déjà pour ce dossier
        if (dossierAnnuel.getVisite() != null) {
            throw new RuntimeException("Une visite existe déjà pour ce dossier annuel");
        }

        // Créer la nouvelle visite
        Visite visite = new Visite();
        visite.setDate(dto.getDate());
        visite.setFormat(dto.getFormat());
        visite.setCommentaires(dto.getCommentaires());
        visite.setDossierAnnuel(dossierAnnuel);

        return visiteRepository.save(visite);
    }

    /**
     * Modifie une visite existante.
     * 
     * @param visiteId l'ID de la visite à modifier
     * @param dto les nouvelles données de la visite
     * @return la visite modifiée
     * @throws RessourceIntrouvableException si la visite n'existe pas
     */
    @Transactional
    public Visite modifierVisite(Integer visiteId, CreerVisiteDto dto) {
        Visite visite = visiteRepository.findById(visiteId)
                .orElseThrow(() -> new RessourceIntrouvableException("Visite", visiteId));

        visite.setDate(dto.getDate());
        visite.setFormat(dto.getFormat());
        visite.setCommentaires(dto.getCommentaires());

        return visiteRepository.save(visite);
    }

    /**
     * Supprime une visite.
     * Gère correctement la relation bidirectionnelle avec le dossier annuel.
     * 
     * @param visiteId l'ID de la visite à supprimer
     * @throws RessourceIntrouvableException si la visite n'existe pas
     */
    @Transactional
    public void supprimerVisite(Integer visiteId) {
        Visite visite = visiteRepository.findById(visiteId)
                .orElseThrow(() -> new RessourceIntrouvableException("Visite", visiteId));

        // Casser la relation bidirectionnelle avec le DossierAnnuel
        DossierAnnuel dossierAnnuel = visite.getDossierAnnuel();
        if (dossierAnnuel != null) {
            dossierAnnuel.setVisite(null);
            dossierAnnuelRepository.save(dossierAnnuel);
        }

        // Supprimer la visite
        visiteRepository.delete(visite);
    }
}

