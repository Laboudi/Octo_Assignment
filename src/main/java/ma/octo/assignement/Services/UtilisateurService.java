package ma.octo.assignement.Services;



import ma.octo.assignement.domain.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    public Utilisateur saveUtilisateur(Utilisateur utilisateur);
    public List<Utilisateur> Utilisateurs();
    public Utilisateur registerNewUser(Utilisateur user);
}
