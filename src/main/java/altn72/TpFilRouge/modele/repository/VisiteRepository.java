package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisiteRepository extends JpaRepository<Visite, Integer> {
    List<Visite> findByApprentiApprentiId(Integer apprentiId);
    List<Visite> findByAnneeAcademiqueAnneeAcademiqueId(Integer anneeAcademiqueId);
}
