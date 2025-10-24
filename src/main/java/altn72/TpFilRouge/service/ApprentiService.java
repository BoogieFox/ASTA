package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.repository.ApprentiRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {
    private final ApprentiRepository apprentiRepository;

    public ApprentiService(ApprentiRepository apprentiRepository) {
        this.apprentiRepository = apprentiRepository;
    }

    public List<Apprenti> getApprentis() {
        return apprentiRepository.findAll();
    }

    public Optional<Apprenti> getUnApprenti(Integer idApprenti) {
        return apprentiRepository.findById(idApprenti);
    }

    @Transactional
    public void supprimerApprenti(Integer idApprenti) {
        Optional<Apprenti> unApprenti = apprentiRepository.findById(idApprenti);

        if (unApprenti.isPresent()) {
            apprentiRepository.deleteById(idApprenti);
        } else {
            throw new IllegalStateException(
                    "L'apprenti dont l'id est " + idApprenti + " n'existe pas");
        }
    }

    @Transactional
    public Apprenti ajouterApprenti(Apprenti apprenti) {
        return apprentiRepository.save(apprenti);
    }

    @Transactional
    public Apprenti modifierApprenti(Integer idApprenti, Apprenti apprentiModified) {
        Apprenti apprentiToModify = apprentiRepository.findById(idApprenti)
                .orElseThrow(() -> new IllegalStateException("L'apprenti dont l'id est " + idApprenti + " n'existe pas"));

        BeanUtils.copyProperties(apprentiModified, apprentiToModify, "apprentiId");
        return apprentiRepository.save(apprentiToModify);
    }
}

