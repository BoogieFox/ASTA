package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.AnneeAcademique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique, Integer> {
    Optional<AnneeAcademique> findByAnneeAcademique(String anneeAcademique);
}
