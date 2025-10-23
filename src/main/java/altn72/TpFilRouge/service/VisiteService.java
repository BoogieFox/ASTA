package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Visite;
import altn72.TpFilRouge.modele.repository.VisiteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {
    private final VisiteRepository visiteRepository;

    public VisiteService(VisiteRepository visiteRepository) {
        this.visiteRepository = visiteRepository;
    }

    public List<Visite> getVisites() {
        return visiteRepository.findAll();
    }

    public Optional<Visite> getUneVisite(Integer id) {
        return visiteRepository.findById(id);
    }

    @Transactional
    public Visite ajouterVisite(Visite visite) {
        return visiteRepository.save(visite);
    }

    @Transactional
    public void supprimerVisite(Integer id) {
        Optional<Visite> visite = visiteRepository.findById(id);
        if (visite.isPresent()) {
            visiteRepository.deleteById(id);
        } else {
            throw new IllegalStateException("La visite avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public Visite modifierVisite(Integer id, Visite visiteModified) {
        Visite visiteToModify = visiteRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La visite avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(visiteModified, visiteToModify, "visiteId");
        return visiteRepository.save(visiteToModify);
    }

    public List<Visite> getVisitesParApprenti(Integer apprentiId) {
        return visiteRepository.findByApprentiApprentiId(apprentiId);
    }

    public List<Visite> getVisitesParAnnee(Integer anneeAcademiqueId) {
        return visiteRepository.findByAnneeAcademiqueAnneeAcademiqueId(anneeAcademiqueId);
    }
}
