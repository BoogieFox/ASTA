package altn72.TpFilRouge.exception;

public class RessourceIntrouvableException extends RuntimeException {
    
    public RessourceIntrouvableException(String ressource, Integer id) {
        super(ressource + " avec l'id " + id + " n'existe pas");
    }
}
