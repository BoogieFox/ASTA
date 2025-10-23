package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Soutenance;
import altn72.TpFilRouge.modele.repository.SoutenanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoutenanceService {
    private final SoutenanceRepository soutenanceRepository;

    public SoutenanceService(SoutenanceRepository soutenanceRepository) {
        this.soutenanceRepository = soutenanceRepository;
    }

    public List<Soutenance> getSoutenances() {
        return soutenanceRepository.findAll();
    }

    public Optional<Soutenance> getUneSoutenance(Integer id) {
        return soutenanceRepository.findById(id);
    }

    @Transactional
    public Soutenance ajouterSoutenance(Soutenance soutenance) {
        return soutenanceRepository.save(soutenance);
    }

    @Transactional
    public void supprimerSoutenance(Integer id) {
        Optional<Soutenance> soutenance = soutenanceRepository.findById(id);
        if (soutenance.isPresent()) {
            soutenanceRepository.deleteById(id);
        } else {
            throw new IllegalStateException("La soutenance avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public Soutenance modifierSoutenance(Integer id, Soutenance soutenanceModified) {
        Soutenance soutenanceToModify = soutenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La soutenance avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(soutenanceModified, soutenanceToModify, "soutenanceId");
        return soutenanceRepository.save(soutenanceToModify);
    }

    public List<Soutenance> getSoutenancesParApprenti(Integer apprentiId) {
        return soutenanceRepository.findByApprentiApprentiId(apprentiId);
    }

    public List<Soutenance> getSoutenancesParAnnee(Integer anneeAcademiqueId) {
        return soutenanceRepository.findByAnneeAcademiqueAnneeAcademiqueId(anneeAcademiqueId);
    }
}
