package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.DossierAnnuel;
import altn72.TpFilRouge.modele.repository.DossierAnnuelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DossierAnnuelService {
    private final DossierAnnuelRepository dossierAnnuelRepository;

    public DossierAnnuelService(DossierAnnuelRepository dossierAnnuelRepository) {
        this.dossierAnnuelRepository = dossierAnnuelRepository;
    }

    public List<DossierAnnuel> getDossiersParApprenti(Integer apprentiId) {
        return dossierAnnuelRepository.findByApprentiApprentiId(apprentiId);
    }

    public Optional<DossierAnnuel> getUnDossierAnnuel(Integer dossierAnnuelId) {
        return dossierAnnuelRepository.findById(dossierAnnuelId);
    }
}

