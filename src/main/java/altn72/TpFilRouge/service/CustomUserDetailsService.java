package altn72.TpFilRouge.service;

import altn72.TpFilRouge.modele.Tuteur;
import altn72.TpFilRouge.modele.repository.TuteurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TuteurRepository repo;

    public CustomUserDetailsService(TuteurRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Tuteur tuteur = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
        return User.builder()
                .username(tuteur.getEmail())
                .password(tuteur.getPassword())
                .roles("TUTOR")
                .build();
    }
}
