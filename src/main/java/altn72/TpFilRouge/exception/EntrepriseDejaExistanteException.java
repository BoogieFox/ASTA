package altn72.TpFilRouge.exception;

public class EntrepriseDejaExistanteException extends RuntimeException {
    
    public EntrepriseDejaExistanteException(String raisonSociale) {
        super("Une entreprise avec la raison sociale \"" + raisonSociale + "\" existe déjà");
    }
}
