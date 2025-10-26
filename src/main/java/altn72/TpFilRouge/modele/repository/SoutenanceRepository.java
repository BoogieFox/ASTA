package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Integer> {
}

