package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DossierAnnuelRepository extends JpaRepository<DossierAnnuel, Integer> {
    
    // Récupérer le dossier d'un apprenti pour une promotion spécifique
    Optional<DossierAnnuel> findByApprentiAndPromotion(Apprenti apprenti, Promotion promotion);
    Optional<DossierAnnuel> findByApprentiApprentiIdAndPromotion(Integer apprentiId, Promotion promotion);
    
    // Vérifier si un dossier existe pour une promotion donnée
    boolean existsByApprentiAndPromotion(Apprenti apprenti, Promotion promotion);
    boolean existsByApprentiApprentiIdAndPromotion(Integer apprentiId, Promotion promotion);
    
    // Récupérer tous les dossiers d'un apprenti
    List<DossierAnnuel> findByApprenti(Apprenti apprenti);
    List<DossierAnnuel> findByApprentiApprentiId(Integer apprentiId);
}
