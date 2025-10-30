package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.Promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DossierAnnuelRepository extends JpaRepository<DossierAnnuel, Integer> {

    Optional<DossierAnnuel> findByApprentiAndPromotion(Apprenti apprenti, Promotion promotion);

    boolean existsByApprentiAndPromotion(Apprenti apprenti, Promotion promotion);
}
