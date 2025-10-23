package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.AnneeAcademique;
import altn72.TpFilRouge.modele.repository.AnneeAcademiqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnneeAcademiqueService {
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public AnneeAcademiqueService(AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }

    public List<AnneeAcademique> getAnneesAcademiques() {
        return anneeAcademiqueRepository.findAll();
    }

    public Optional<AnneeAcademique> getUneAnneeAcademique(Integer id) {
        return anneeAcademiqueRepository.findById(id);
    }

    @Transactional
    public AnneeAcademique ajouterAnneeAcademique(AnneeAcademique anneeAcademique) {
        return anneeAcademiqueRepository.save(anneeAcademique);
    }

    @Transactional
    public void supprimerAnneeAcademique(Integer id) {
        Optional<AnneeAcademique> anneeAcademique = anneeAcademiqueRepository.findById(id);
        if (anneeAcademique.isPresent()) {
            anneeAcademiqueRepository.deleteById(id);
        } else {
            throw new IllegalStateException("L'année académique avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public AnneeAcademique modifierAnneeAcademique(Integer id, AnneeAcademique anneeAcademiqueModified) {
        AnneeAcademique anneeAcademiqueToModify = anneeAcademiqueRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("L'année académique avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(anneeAcademiqueModified, anneeAcademiqueToModify, "anneeAcademiqueId", "apprentis");
        return anneeAcademiqueRepository.save(anneeAcademiqueToModify);
    }
}
