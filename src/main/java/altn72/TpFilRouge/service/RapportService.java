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


}
