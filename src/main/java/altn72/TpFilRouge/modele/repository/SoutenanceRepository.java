package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Integer> {
    List<Soutenance> findByApprentiApprentiId(Integer apprentiId);
    List<Soutenance> findByAnneeAcademiqueAnneeAcademiqueId(Integer anneeAcademiqueId);
}
