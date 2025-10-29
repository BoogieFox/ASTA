package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.ApprentiDejaExistantException;
import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.Promotion;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.dto.ModifierApprentiDto;
import altn72.TpFilRouge.modele.repository.ApprentiRepository;
import altn72.TpFilRouge.modele.repository.EntrepriseRepository;
import altn72.TpFilRouge.modele.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des apprentis.
 * Gère la logique métier liée aux apprentis : création, modification, archivage,
 * passage de promotion, et gestion des relations avec les entreprises et maîtres d'apprentissage.
 */
@Service
public class ApprentiService {
    private final ApprentiRepository apprentiRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final MaitreApprentissageRepository maitreApprentissageRepository;
    private final DossierAnnuelService dossierAnnuelService;

    public ApprentiService(ApprentiRepository apprentiRepository, 
                          EntrepriseRepository entrepriseRepository,
                          MaitreApprentissageRepository maitreApprentissageRepository,
                          DossierAnnuelService dossierAnnuelService) {
        this.apprentiRepository = apprentiRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.maitreApprentissageRepository = maitreApprentissageRepository;
        this.dossierAnnuelService = dossierAnnuelService;
    }

    /**
     * Récupère tous les apprentis.
     * 
     * @return la liste de tous les apprentis (actifs et archivés)
     */
    public List<Apprenti> getApprentis() {
        return apprentiRepository.findAll();
    }

    /**
     * Récupère tous les apprentis actifs (non archivés).
     * Un apprenti est considéré comme actif si sa promotion est L1, L2 ou L3.
     *
     * @return la liste des apprentis actifs
     */
    public List<Apprenti> getApprentisActifs() {
        return apprentiRepository.findAll().stream()
                .filter(apprenti -> apprenti.getPromotion() != Promotion.ARCHIVE)
                .toList();
    }

    /**
     * Récupère tous les apprentis archivés.
     * Un apprenti est considéré comme archivé si sa promotion est ARCHIVE.
     *
     * @return la liste des apprentis archivés
     */
    public List<Apprenti> getApprentisArchives() {
        return apprentiRepository.findAll().stream()
                .filter(apprenti -> apprenti.getPromotion() == Promotion.ARCHIVE)
                .toList();
    }
    
    /**
     * Recherche des apprentis actifs selon un terme et un mode de recherche spécifique.
     * 
     * @param searchTerm le terme de recherche
     * @param searchMode le mode de recherche : "tout", "apprenti", "entreprise", "mission"
     * @return la liste des apprentis actifs correspondants
     */
    public List<Apprenti> searchApprentisActifs(String searchTerm, String searchMode) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getApprentisActifs();
        }
        
        List<Apprenti> results = switch (searchMode) {
            case "apprenti" -> apprentiRepository.searchApprentisByName(searchTerm.trim(), null);
            case "entreprise" -> apprentiRepository.searchApprentisByEntreprise(searchTerm.trim(), null);
            case "mission" -> apprentiRepository.searchApprentisByMission(searchTerm.trim(), null);
            default -> apprentiRepository.searchApprentis(searchTerm.trim(), null); // "tout"
        };
        
        return results.stream()
                .filter(apprenti -> apprenti.getPromotion() != Promotion.ARCHIVE)
                .toList();
    }
    
    /**
     * Recherche des apprentis archivés selon un terme et un mode de recherche spécifique.
     * 
     * @param searchTerm le terme de recherche
     * @param searchMode le mode de recherche : "tout", "apprenti", "entreprise", "mission"
     * @return la liste des apprentis archivés correspondants
     */
    public List<Apprenti> searchApprentisArchives(String searchTerm, String searchMode) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getApprentisArchives();
        }
        
        return switch (searchMode) {
            case "apprenti" -> apprentiRepository.searchApprentisByName(searchTerm.trim(), Promotion.ARCHIVE);
            case "entreprise" -> apprentiRepository.searchApprentisByEntreprise(searchTerm.trim(), Promotion.ARCHIVE);
            case "mission" -> apprentiRepository.searchApprentisByMission(searchTerm.trim(), Promotion.ARCHIVE);
            default -> apprentiRepository.searchApprentis(searchTerm.trim(), Promotion.ARCHIVE); // "tout"
        };
    }

    /**
     * Récupère un apprenti par son identifiant.
     * 
     * @param idApprenti l'ID de l'apprenti
     * @return un Optional contenant l'apprenti s'il existe, Optional.empty() sinon
     */
    public Optional<Apprenti> getUnApprenti(Integer idApprenti) {
        return apprentiRepository.findById(idApprenti);
    }

    /**
     * Ajoute un nouvel apprenti dans le système.
     * Crée automatiquement un dossier annuel pour la promotion L1.
     * Vérifie que l'email n'est pas déjà utilisé.
     * 
     * @param dto les données de l'apprenti à créer
     * @return l'apprenti créé avec son ID généré
     * @throws ApprentiDejaExistantException si l'email existe déjà
     * @throws RessourceIntrouvableException si l'entreprise ou le maître d'apprentissage spécifié n'existe pas
     */
    @Transactional
    public Apprenti ajouterApprenti(CreerApprentiDto dto) {
        // Check si le mail est déjà utilisé
        if (apprentiRepository.existsByEmail(dto.getEmail())) {
            throw new ApprentiDejaExistantException(dto.getEmail());
        }

        // Créer un nouvel apprenti et mapper uniquement les propriétés simples
        Apprenti apprenti = new Apprenti();
        apprenti.setNom(dto.getNom());
        apprenti.setPrenom(dto.getPrenom());
        apprenti.setEmail(dto.getEmail());
        apprenti.setTelephone(dto.getTelephone());
        apprenti.setMajeure(dto.getMajeure());
        apprenti.setPromotion(Promotion.L1);

        DossierAnnuel dossierInitial = new DossierAnnuel(apprenti, Promotion.L1);
        apprenti.ajouterDossierAnnuel(dossierInitial);

        // Associer l'entreprise existante si elle est spécifiée
        if (dto.getEntrepriseId() != null) {
            Entreprise entreprise = entrepriseRepository.findById(dto.getEntrepriseId())
                    .orElseThrow(() -> new RessourceIntrouvableException("Entreprise", dto.getEntrepriseId()));
            apprenti.setEntreprise(entreprise);
        }

        // Associer le MA existant si il est spécifié
        if (dto.getMaitreApprentissageId() != null) {
            MaitreApprentissage maitreApprentissage = maitreApprentissageRepository.findById(dto.getMaitreApprentissageId())
                    .orElseThrow(() -> new RessourceIntrouvableException("Maître d'apprentissage", dto.getMaitreApprentissageId()));
            apprenti.setMaitreApprentissage(maitreApprentissage);
        }

        return apprentiRepository.save(apprenti);
    }

    /**
     * Modifie les informations personnelles d'un apprenti existant.
     * Vérifie que l'apprenti existe et que l'email n'est pas déjà utilisé par un autre apprenti.
     * 
     * @param apprentiId l'ID de l'apprenti à modifier
     * @param dto les nouvelles informations de l'apprenti
     * @return l'apprenti mis à jour
     * @throws RessourceIntrouvableException si l'apprenti n'existe pas
     * @throws ApprentiDejaExistantException si l'email est déjà utilisé par un autre apprenti
     */
    @Transactional
    public Apprenti modifierApprenti(Integer apprentiId, ModifierApprentiDto dto) {
        // Vérifier que l'apprenti existe
        Apprenti apprenti = apprentiRepository.findById(apprentiId)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", apprentiId));

        // Vérifier que l'email n'est pas déjà utilisé par un autre apprenti
        if (apprentiRepository.existsByEmailAndApprentiIdNot(dto.getEmail(), apprentiId)) {
            throw new ApprentiDejaExistantException(dto.getEmail());
        }

        // Mettre à jour les informations personnelles
        apprenti.setNom(dto.getNom());
        apprenti.setPrenom(dto.getPrenom());
        apprenti.setEmail(dto.getEmail());
        apprenti.setTelephone(dto.getTelephone());
        apprenti.setMajeure(dto.getMajeure());

        return apprentiRepository.save(apprenti);
    }

    /**
     * Modifie l'entreprise d'un apprenti existant.
     *
     * @param apprentiId l'ID de l'apprenti à modifier
     * @param entrepriseId l'ID de la nouvelle entreprise (peut être null pour retirer l'entreprise)
     * @return l'apprenti mis à jour
     * @throws RessourceIntrouvableException si l'apprenti ou l'entreprise n'existe pas
     */
    @Transactional
    public Apprenti modifierEntreprise(Integer apprentiId, Integer entrepriseId) {
        Apprenti apprenti = apprentiRepository.findById(apprentiId)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", apprentiId));

        if (entrepriseId != null) {
            Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                    .orElseThrow(() -> new RessourceIntrouvableException("Entreprise", entrepriseId));
            apprenti.setEntreprise(entreprise);
        } else {
            apprenti.setEntreprise(null);
        }

        return apprentiRepository.save(apprenti);
    }

    /**
     * Modifie le maître d'apprentissage d'un apprenti existant.
     *
     * @param apprentiId l'ID de l'apprenti à modifier
     * @param maitreApprentissageId l'ID du nouveau maître d'apprentissage (peut être null pour retirer le maître)
     * @return l'apprenti mis à jour
     * @throws RessourceIntrouvableException si l'apprenti ou le maître d'apprentissage n'existe pas
     */
    @Transactional
    public Apprenti modifierMaitreApprentissage(Integer apprentiId, Integer maitreApprentissageId) {
        Apprenti apprenti = apprentiRepository.findById(apprentiId)
                .orElseThrow(() -> new RessourceIntrouvableException("Apprenti", apprentiId));

        if (maitreApprentissageId != null) {
            MaitreApprentissage maitreApprentissage = maitreApprentissageRepository.findById(maitreApprentissageId)
                    .orElseThrow(() -> new RessourceIntrouvableException("Maître d'apprentissage", maitreApprentissageId));
            apprenti.setMaitreApprentissage(maitreApprentissage);
        } else {
            apprenti.setMaitreApprentissage(null);
        }

        return apprentiRepository.save(apprenti);
    }

    /**
     * Fait passer tous les apprentis à la promotion suivante et crée automatiquement
     * un nouveau dossier annuel pour chaque apprenti qui change de promotion.
     * 
     * @return le nombre d'apprentis qui ont changé de promotion
     */
    @Transactional
    public int commencerNouvelleAnnee() {
        List<Apprenti> tousLesApprentis = apprentiRepository.findAll();
        int apprentisModifies = 0;

        for (Apprenti apprenti : tousLesApprentis) {
            Promotion anciennePromotion = apprenti.getPromotion();
            Promotion nouvellePromotion = anciennePromotion.getPromotionSuivante();

            // Ne pas modifier les apprentis déjà archivés
            if (anciennePromotion != nouvellePromotion) {
                apprenti.setPromotion(nouvellePromotion);
                apprentiRepository.save(apprenti);

                // Créer automatiquement un nouveau dossier pour la nouvelle promotion
                try {
                    dossierAnnuelService.creerDossierCourant(apprenti.getApprentiId());
                    apprentisModifies++;
                } catch (IllegalStateException e) {
                    // Si un dossier existe déjà, on continue sans erreur
                    // Cela peut arriver si la méthode est appelée plusieurs fois
                    apprentisModifies++;
                }
            }
        }

        return apprentisModifies;
    }

}

