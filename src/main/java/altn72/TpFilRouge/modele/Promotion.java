package altn72.TpFilRouge.modele;

import lombok.Getter;

@Getter
public enum Promotion {
    L1("L1"),
    L2("L2"),
    L3("L3"),
    ARCHIVE("ARCHIVE");

    private final String label;

    Promotion(String label) {
        this.label = label;
    }

    public Promotion getPromotionSuivante() {
        return switch (this) {
            case L1 -> L2;
            case L2 -> L3;
            case L3, ARCHIVE -> ARCHIVE;
        };
    }

    @Override
    public String toString() {
        return label;
    }
}
