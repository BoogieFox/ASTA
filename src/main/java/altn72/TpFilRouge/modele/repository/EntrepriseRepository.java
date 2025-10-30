package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    boolean existsByRaisonSociale(String raisonSociale);
}
