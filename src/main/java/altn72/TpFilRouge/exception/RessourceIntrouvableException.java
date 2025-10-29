package altn72.TpFilRouge.exception;

/**
 * Exception levée lorsqu'une ressource demandée n'existe pas en base de données.
 * Utilisée pour signaler l'absence d'une entité (Apprenti, Entreprise, etc.) lors d'une recherche par ID.
 */
public class RessourceIntrouvableException extends RuntimeException {
    
    /**
     * Construit une exception avec un message formaté.
     * 
     * @param ressource le type de ressource recherchée (ex: "Apprenti", "Entreprise")
     * @param id l'identifiant de la ressource recherchée
     */
    public RessourceIntrouvableException(String ressource, Integer id) {
        super(ressource + " avec l'id " + id + " n'existe pas");
    }
}
