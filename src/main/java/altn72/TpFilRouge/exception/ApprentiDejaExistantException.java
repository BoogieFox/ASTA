package altn72.TpFilRouge.exception;

/**
 * Exception levée lorsqu'on tente de créer un apprenti avec un email déjà utilisé.
 * L'email doit être unique dans le système.
 */
public class ApprentiDejaExistantException extends RuntimeException {
    
    /**
     * Construit une exception avec un message incluant l'email en conflit.
     * 
     * @param email l'adresse email déjà utilisée
     */
    public ApprentiDejaExistantException(String email) {
        super("Un apprenti avec l'email \"" + email + "\" existe déjà");
    }
}
