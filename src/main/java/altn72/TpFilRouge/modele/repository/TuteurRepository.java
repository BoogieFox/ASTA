package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TuteurRepository extends JpaRepository<Tuteur, Integer> {
    Optional<Tuteur> findByEmail(String email);
}
