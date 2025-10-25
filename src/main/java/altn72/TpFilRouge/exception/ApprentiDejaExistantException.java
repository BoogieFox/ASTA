package altn72.TpFilRouge.exception;

public class ApprentiDejaExistantException extends RuntimeException {
    
    public ApprentiDejaExistantException(String email) {
        super("Un apprenti avec l'email \"" + email + "\" existe déjà");
    }
}
