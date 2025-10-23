package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
    List<Rapport> findByApprentiApprentiId(Integer apprentiId);
}
