package altn72.TpFilRouge.service;

import altn72.TpFilRouge.exception.ApprentiDejaExistantException;
import altn72.TpFilRouge.exception.RessourceIntrouvableException;
import altn72.TpFilRouge.modele.Apprenti;
import altn72.TpFilRouge.modele.Entreprise;
import altn72.TpFilRouge.modele.MaitreApprentissage;
import altn72.TpFilRouge.modele.dto.CreerApprentiDto;
import altn72.TpFilRouge.modele.repository.ApprentiRepository;
import altn72.TpFilRouge.modele.repository.EntrepriseRepository;
import altn72.TpFilRouge.modele.repository.MaitreApprentissageRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {
    private final ApprentiRepository apprentiRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final MaitreApprentissageRepository maitreApprentissageRepository;
    private final ModelMapper modelMapper;

    public ApprentiService(ApprentiRepository apprentiRepository, 
                          EntrepriseRepository entrepriseRepository,
                          MaitreApprentissageRepository maitreApprentissageRepository,
                          ModelMapper modelMapper) {
        this.apprentiRepository = apprentiRepository;
        this.entrepriseRepository = entrepriseRepository;
        this.maitreApprentissageRepository = maitreApprentissageRepository;
        this.modelMapper = modelMapper;
    }

    public List<Apprenti> getApprentis() {
        return apprentiRepository.findAll();
    }

    public Optional<Apprenti> getUnApprenti(Integer idApprenti) {
        return apprentiRepository.findById(idApprenti);
    }

    @Transactional
    public Apprenti ajouterApprenti(CreerApprentiDto dto) {
        // Check si le mail est déjà utilisé
        if (apprentiRepository.existsByEmail(dto.getEmail())) {
            throw new ApprentiDejaExistantException(dto.getEmail());
        }

        Apprenti apprenti = modelMapper.map(dto, Apprenti.class);

        // Associer l'entreprise
        if (dto.getEntrepriseId() != null) {
            Entreprise entreprise = entrepriseRepository.findById(dto.getEntrepriseId())
                    .orElseThrow(() -> new RessourceIntrouvableException("Entreprise", dto.getEntrepriseId()));
            apprenti.setEntreprise(entreprise);
        }

        // Associer le MA
        if (dto.getMaitreApprentissageId() != null) {
            MaitreApprentissage maitreApprentissage = maitreApprentissageRepository.findById(dto.getMaitreApprentissageId())
                    .orElseThrow(() -> new RessourceIntrouvableException("Maître d'apprentissage", dto.getMaitreApprentissageId()));
            apprenti.setMaitreApprentissage(maitreApprentissage);
        }

        return apprentiRepository.save(apprenti);
    }

}

