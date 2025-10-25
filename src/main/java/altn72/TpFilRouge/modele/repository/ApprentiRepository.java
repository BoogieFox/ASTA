package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    
    boolean existsByEmail(String email);
    
    Optional<Apprenti> findByEmail(String email);
    

   //Pour la modification, on vérifie si son email est déja utilisé (en l'excluant lui même)
    boolean existsByEmailAndApprentiIdNot(String email, Integer apprentiId);
}
