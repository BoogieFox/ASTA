package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RapportRepository extends JpaRepository<Rapport, Integer> {
}
