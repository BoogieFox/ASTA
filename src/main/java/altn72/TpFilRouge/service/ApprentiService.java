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
    private final ApprentiRepository ApprentiRepository;

    public ApprentiService(ApprentiRepository ApprentiRepository) {
        this.ApprentiRepository = ApprentiRepository;
    }

    public List<Apprenti> getApprentis() {
        return ApprentiRepository.findAll();
    }

    public Optional<Apprenti> getUnApprenti(Integer idApprenti) {
        Optional<Apprenti> unApprenti = ApprentiRepository.findById(idApprenti);

        return Optional.ofNullable(
                unApprenti.orElseThrow(
                        () -> new IllegalStateException(
                                "Le Apprenti dont l'id est " + idApprenti + " n'existe pas")));
    }

    @Transactional
    public void supprimerApprenti(Integer idApprenti) {
        Optional<Apprenti> unApprenti = ApprentiRepository.findById(idApprenti);

        if (unApprenti.isPresent()) {
            ApprentiRepository.deleteById(idApprenti);
        } else {
            throw new IllegalStateException(
                    "Le Apprenti dont l'id est " + idApprenti + " n'existe pas");
        }
    }

    @Transactional
    public void ajouterApprenti(Apprenti Apprenti) {
        ApprentiRepository.save(Apprenti);
    }

    @Transactional
    public void modifierApprenti(
            Integer idApprenti,
            Apprenti ApprentiModified) {
        Apprenti ApprentiToModify = ApprentiRepository.findById(idApprenti).orElseThrow();

        if (ApprentiToModify != null) {
            BeanUtils.copyProperties(ApprentiModified, ApprentiToModify, "id");
            ApprentiRepository.save(ApprentiToModify);
        }
    }
}

