package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaitreApprentissageService {
    private final MaitreApprentissageRepository maitreApprentissageRepository;

    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
    }

    public List<MaitreApprentissage> getMaitresApprentissage() {
        return maitreApprentissageRepository.findAll();
    }

    public Optional<MaitreApprentissage> getUnMaitreApprentissage(Integer id) {
        return maitreApprentissageRepository.findById(id);
    }

    @Transactional
    public MaitreApprentissage ajouterMaitreApprentissage(MaitreApprentissage maitreApprentissage) {
        return maitreApprentissageRepository.save(maitreApprentissage);
    }

    @Transactional
    public void supprimerMaitreApprentissage(Integer id) {
        Optional<MaitreApprentissage> maitreApprentissage = maitreApprentissageRepository.findById(id);
        if (maitreApprentissage.isPresent()) {
            maitreApprentissageRepository.deleteById(id);
        } else {
            throw new IllegalStateException("Le maître d'apprentissage avec l'id " + id + " n'existe pas");
        }
    }

    @Transactional
    public MaitreApprentissage modifierMaitreApprentissage(Integer id, MaitreApprentissage maitreApprentissageModified) {
        MaitreApprentissage maitreApprentissageToModify = maitreApprentissageRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Le maître d'apprentissage avec l'id " + id + " n'existe pas"));
        
        BeanUtils.copyProperties(maitreApprentissageModified, maitreApprentissageToModify, "maitreApprentissageId", "apprentis");
        return maitreApprentissageRepository.save(maitreApprentissageToModify);
    }

    public List<MaitreApprentissage> getMaitresParEntreprise(Integer entrepriseId) {
        return maitreApprentissageRepository.findByEntrepriseEntrepriseId(entrepriseId);
    }
}
