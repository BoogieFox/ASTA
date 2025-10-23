package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findByApprentiApprentiId(Integer apprentiId);
    List<Mission> findByAnneeAcademiqueAnneeAcademiqueId(Integer anneeAcademiqueId);
}
