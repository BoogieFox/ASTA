package altn72.TpFilRouge.exception;

public class MaitreApprentissageDejaExistantException extends RuntimeException {

    public MaitreApprentissageDejaExistantException(String email) {
        super("Un maître d'apprentissage avec l'email \"" + email + "\" existe déjà");
    }
}

