package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.Services.UtilisateurService;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Utilisateur> Utilisateurs() {
        List<Utilisateur> utilisateurs=utilisateurRepository.findAll();
        if(CollectionUtils.isEmpty(utilisateurs)){
            return null;
        }
        return utilisateurs;
    }
    @Override
    public Utilisateur registerNewUser(Utilisateur user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return utilisateurRepository.save(user);
    }
}
