package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.Services.CompteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    @Override
    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public List<Compte> Comptes() {
        List<Compte> comptes=compteRepository.findAll();
        if(CollectionUtils.isEmpty(comptes)){
            return null;
        }
        return comptes;
    }
}
