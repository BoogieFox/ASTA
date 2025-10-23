package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Rapport;
import altn72.TpFilRouge.modele.repository.RapportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RapportService {
    private final RapportRepository rapportRepository;

    public RapportService(RapportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    public List<Rapport> getRapports() {
        return rapportRepository.findAll();
    }

    public Optional<Rapport> getUnRapport(Integer id) {
        return rapportRepository.findById(id);
    }

    @Transactional
    public Rapport ajouterRapport(Rapport rapport) {
        return rapportRepository.save(rapport);
    }

    @Transactional
    public void supprimerRapport(Integer id) {
        Optional<Rapport> rapport = rapportRepository.findById(id);
        if (rapport.isPresent()) {
            rapportRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Le rapport avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public Rapport modifierRapport(Integer id, Rapport rapportModified) {
        Rapport rapportToModify = rapportRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Le rapport avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(rapportModified, rapportToModify, "rapportId");
        return rapportRepository.save(rapportToModify);
    }

    public List<Rapport> getRapportsParApprenti(Integer apprentiId) {
        return rapportRepository.findByApprentiApprentiId(apprentiId);
    }
}
