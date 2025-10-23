package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Mission;
import altn72.TpFilRouge.modele.repository.MissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService {
    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public List<Mission> getMissions() {
        return missionRepository.findAll();
    }

    public Optional<Mission> getUneMission(Integer id) {
        return missionRepository.findById(id);
    }

    @Transactional
    public Mission ajouterMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Transactional
    public void supprimerMission(Integer id) {
        Optional<Mission> mission = missionRepository.findById(id);
        if (mission.isPresent()) {
            missionRepository.deleteById(id);
        } else {
            throw new IllegalStateException("La mission avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public Mission modifierMission(Integer id, Mission missionModified) {
        Mission missionToModify = missionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("La mission avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(missionModified, missionToModify, "missionId");
        return missionRepository.save(missionToModify);
    }

    public List<Mission> getMissionsParApprenti(Integer apprentiId) {
        return missionRepository.findByApprentiApprentiId(apprentiId);
    }

    public List<Mission> getMissionsParAnnee(Integer anneeAcademiqueId) {
        return missionRepository.findByAnneeAcademiqueAnneeAcademiqueId(anneeAcademiqueId);
    }
}
