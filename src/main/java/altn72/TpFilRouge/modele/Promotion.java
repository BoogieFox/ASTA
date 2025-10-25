package altn72.TpFilRouge.modele;

public enum Promotion {
    L1("L1"),
    L2("L2"),
    L3("L3");

    private final String label;

    Promotion(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
