package ma.octo.assignement.Services;

import ma.octo.assignement.domain.Compte;

import java.util.List;

public interface CompteService {
    public Compte saveCompte(Compte compte);
    public List<Compte> Comptes();
}
