package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiteRepository extends JpaRepository<Visite, Integer> {
}

