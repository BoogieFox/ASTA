package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.MaitreApprentissageDejaExistantException;
import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des maîtres d'apprentissage.
 * Gère la logique métier liée aux maîtres d'apprentissage : création, consultation et validation.
 */
@Service
public class MaitreApprentissageService {
    private final MaitreApprentissageRepository maitreApprentissageRepository;

    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
    }

    /**
     * Récupère tous les maîtres d'apprentissage.
     * 
     * @return la liste de tous les maîtres d'apprentissage
     */
    public List<MaitreApprentissage> getMaitresApprentissage() {
        return maitreApprentissageRepository.findAll();
    }

    /**
     * Récupère un maître d'apprentissage par son identifiant.
     * 
     * @param id l'ID du maître d'apprentissage
     * @return un Optional contenant le maître d'apprentissage s'il existe, Optional.empty() sinon
     */
    public Optional<MaitreApprentissage> getUnMaitreApprentissage(Integer id) {
        return maitreApprentissageRepository.findById(id);
    }

    /**
     * Ajoute un nouveau maître d'apprentissage dans le système.
     * Vérifie que l'email n'existe pas déjà.
     * 
     * @param maitreApprentissage le maître d'apprentissage à créer
     * @return le maître d'apprentissage créé avec son ID généré
     * @throws MaitreApprentissageDejaExistantException si l'email existe déjà
     */
    @Transactional
    public MaitreApprentissage ajouterMaitreApprentissage(MaitreApprentissage maitreApprentissage) {
        if (maitreApprentissageRepository.existsByEmail(maitreApprentissage.getEmail())) {
            throw new MaitreApprentissageDejaExistantException(maitreApprentissage.getEmail());
        }
        return maitreApprentissageRepository.save(maitreApprentissage);
    }


}
