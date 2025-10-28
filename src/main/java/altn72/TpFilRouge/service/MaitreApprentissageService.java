package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.MaitreApprentissageDejaExistantException;
import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
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
        if (maitreApprentissageRepository.existsByEmail(maitreApprentissage.getEmail())) {
            throw new MaitreApprentissageDejaExistantException(maitreApprentissage.getEmail());
        }
        return maitreApprentissageRepository.save(maitreApprentissage);
    }


}
