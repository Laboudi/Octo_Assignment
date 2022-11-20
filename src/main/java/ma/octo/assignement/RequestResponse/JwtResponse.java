package ma.octo.assignement.RequestResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.domain.Utilisateur;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Utilisateur user;
    private String token;
}