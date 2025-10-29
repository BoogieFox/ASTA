package altn72.TpFilRouge.exception;

/**
 * Exception levée lorsqu'on tente de créer une entreprise avec une raison sociale déjà utilisée.
 * La raison sociale doit être unique dans le système.
 */
public class EntrepriseDejaExistanteException extends RuntimeException {
    
    /**
     * Construit une exception avec un message incluant la raison sociale en conflit.
     * 
     * @param raisonSociale la raison sociale déjà utilisée
     */
    public EntrepriseDejaExistanteException(String raisonSociale) {
        super("Une entreprise avec la raison sociale \"" + raisonSociale + "\" existe déjà");
    }
}
