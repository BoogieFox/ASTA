package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.DossierAnnuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DossierAnnuelRepository extends JpaRepository<DossierAnnuel, Integer> {

    List<DossierAnnuel> findByApprentiApprentiId(Integer apprentiId);
}

