package altn72.TpFilRouge.exception;

/**
 * Exception levée lorsqu'on tente de créer un maître d'apprentissage avec un email déjà utilisé.
 * L'email doit être unique dans le système.
 */
public class MaitreApprentissageDejaExistantException extends RuntimeException {

    /**
     * Construit une exception avec un message incluant l'email en conflit.
     * 
     * @param email l'adresse email déjà utilisée
     */
    public MaitreApprentissageDejaExistantException(String email) {
        super("Un maître d'apprentissage avec l'email \"" + email + "\" existe déjà");
    }
}

