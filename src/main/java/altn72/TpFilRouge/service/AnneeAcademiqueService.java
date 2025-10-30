package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.AnneeAcademiqueCourante;
import altn72.TpFilRouge.modele.repository.AnneeAcademiqueCouranteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AnneeAcademiqueService {
    private static final Long UNIQUE_ID = 1L;
    private static final String ACADEMIC_YEAR_PATTERN = "\\d{4}-\\d{4}";

    private final AnneeAcademiqueCouranteRepository repository;

    public AnneeAcademiqueService(AnneeAcademiqueCouranteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String getAnneeAcademiqueCourante() {
        return chargerOuInitialiser().getValeur();
    }

    @Transactional
    public String incrementerAnneeAcademiqueCourante() {
        AnneeAcademiqueCourante courante = chargerOuInitialiser();
        String nouvelleValeur = incrementerValeur(courante.getValeur());
        courante.setValeur(nouvelleValeur);
        repository.save(courante);
        return nouvelleValeur;
    }

    private AnneeAcademiqueCourante chargerOuInitialiser() {
        return repository.findById(UNIQUE_ID)
                .orElseGet(() -> repository.save(new AnneeAcademiqueCourante(UNIQUE_ID, calculerAnneeAcademiqueDepuisDate())));
    }

    private String incrementerValeur(String valeur) {
        String reference = valeur;
        if (reference == null || !reference.matches(ACADEMIC_YEAR_PATTERN)) {
            reference = calculerAnneeAcademiqueDepuisDate();
        }
        int anneeDebut = Integer.parseInt(reference.substring(0, 4)) + 1;
        return formaterAnneeAcademique(anneeDebut);
    }

    private String calculerAnneeAcademiqueDepuisDate() {
        LocalDate maintenant = LocalDate.now();
        int anneeDebut = maintenant.getMonthValue() >= 9 ? maintenant.getYear() : maintenant.getYear() - 1;
        return formaterAnneeAcademique(anneeDebut);
    }

    private String formaterAnneeAcademique(int anneeDebut) {
        return anneeDebut + "-" + (anneeDebut + 1);
    }
}
