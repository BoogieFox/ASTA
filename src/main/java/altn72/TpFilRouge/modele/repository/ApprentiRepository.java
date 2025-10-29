package altn72.TpFilRouge.modele.repository;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    
    boolean existsByEmail(String email);
    
    Optional<Apprenti> findByEmail(String email);
    

   //Pour la modification, on vérifie si son email est déja utilisé (en l'excluant lui même)
    boolean existsByEmailAndApprentiIdNot(String email, Integer apprentiId);
    
    /**
     * Recherche des apprentis selon plusieurs critères (nom, prénom, entreprise, mots-clés de mission).
     * Recherche insensible à la casse.
     * 
     * @param searchTerm le terme de recherche
     * @param promotion la promotion pour filtrer (null pour toutes)
     * @return la liste des apprentis correspondants
     */
    @Query("SELECT DISTINCT a FROM Apprenti a " +
           "LEFT JOIN a.entreprise e " +
           "LEFT JOIN a.mission m " +
           "WHERE (:promotion IS NULL OR a.promotion = :promotion) " +
           "AND (:searchTerm IS NULL OR :searchTerm = '' OR " +
           "LOWER(a.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(a.prenom, ' ', a.nom)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(a.nom, ' ', a.prenom)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.raisonSociale) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.motsCles) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Apprenti> searchApprentis(@Param("searchTerm") String searchTerm, @Param("promotion") Promotion promotion);
    
    /**
     * Recherche des apprentis uniquement par nom/prénom.
     * 
     * @param searchTerm le terme de recherche
     * @param promotion la promotion pour filtrer (null pour toutes)
     * @return la liste des apprentis correspondants
     */
    @Query("SELECT DISTINCT a FROM Apprenti a " +
           "WHERE (:promotion IS NULL OR a.promotion = :promotion) " +
           "AND (:searchTerm IS NULL OR :searchTerm = '' OR " +
           "LOWER(a.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(a.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(a.prenom, ' ', a.nom)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(a.nom, ' ', a.prenom)) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Apprenti> searchApprentisByName(@Param("searchTerm") String searchTerm, @Param("promotion") Promotion promotion);
    
    /**
     * Recherche des apprentis uniquement par entreprise.
     * 
     * @param searchTerm le terme de recherche
     * @param promotion la promotion pour filtrer (null pour toutes)
     * @return la liste des apprentis correspondants
     */
    @Query("SELECT DISTINCT a FROM Apprenti a " +
           "LEFT JOIN a.entreprise e " +
           "WHERE (:promotion IS NULL OR a.promotion = :promotion) " +
           "AND (:searchTerm IS NULL OR :searchTerm = '' OR " +
           "LOWER(e.raisonSociale) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Apprenti> searchApprentisByEntreprise(@Param("searchTerm") String searchTerm, @Param("promotion") Promotion promotion);
    
    /**
     * Recherche des apprentis uniquement par mots-clés de mission.
     * 
     * @param searchTerm le terme de recherche
     * @param promotion la promotion pour filtrer (null pour toutes)
     * @return la liste des apprentis correspondants
     */
    @Query("SELECT DISTINCT a FROM Apprenti a " +
           "LEFT JOIN a.mission m " +
           "WHERE (:promotion IS NULL OR a.promotion = :promotion) " +
           "AND (:searchTerm IS NULL OR :searchTerm = '' OR " +
           "LOWER(m.motsCles) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Apprenti> searchApprentisByMission(@Param("searchTerm") String searchTerm, @Param("promotion") Promotion promotion);
}
