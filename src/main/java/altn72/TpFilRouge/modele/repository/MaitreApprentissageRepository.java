package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.MaitreApprentissage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaitreApprentissageRepository extends JpaRepository<MaitreApprentissage, Integer> {

    boolean existsByEmail(String email);

}

