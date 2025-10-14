package altn72.TpFilRouge.controleur;

import altn72.TpFilRouge.modele.Programmeur;
import altn72.TpFilRouge.modele.ProgrammeurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeurService {
    private final ProgrammeurRepository programmeurRepository;

    public ProgrammeurService(ProgrammeurRepository programmeurRepository) {
        this.programmeurRepository = programmeurRepository;
    }

    public List<Programmeur> getProgrammeurs() {
        return programmeurRepository.findAll();
    }

    public Optional<Programmeur> getUnProgrammeur(Integer idProgrammeur) {
        Optional<Programmeur> unProgrammeur = programmeurRepository.findById(idProgrammeur);

        return Optional.ofNullable(
                unProgrammeur.orElseThrow(
                        () -> new IllegalStateException(
                                "Le programmeur dont l\'id est " + idProgrammeur + " n\'existe pas")));
    }

    @Transactional
    public void supprimerProgrammeur(Integer idProgrammeur) {
        Optional<Programmeur> unProgrammeur = programmeurRepository.findById(idProgrammeur);

        if (unProgrammeur.isPresent()) {
            programmeurRepository.deleteById(idProgrammeur);
        } else {
            throw new IllegalStateException(
                    "Le programmeur dont l\'id est " + idProgrammeur + " n\'existe pas");
        }
    }

    @Transactional
    public void ajouterProgrammeur(Programmeur programmeur) {
        programmeurRepository.save(programmeur);
    }

    @Transactional
    public void modifierProgrammeur(
            Integer idProgrammeur,
            Programmeur programmeurModified) {
        Programmeur programmeurToModify = programmeurRepository.findById(idProgrammeur).orElseThrow();

        if (programmeurToModify != null) {
            BeanUtils.copyProperties(programmeurModified, programmeurToModify, "id");
            programmeurRepository.save(programmeurToModify);
        }
    }
}

