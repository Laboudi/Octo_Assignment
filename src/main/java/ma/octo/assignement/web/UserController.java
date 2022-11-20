package ma.octo.assignement.web;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.RequestResponse.JwtRequest;
import ma.octo.assignement.RequestResponse.JwtResponse;
import ma.octo.assignement.ServiceImpl.RoleServiceImpl;
import ma.octo.assignement.ServiceImpl.UtilisateurServiceImpl;
import ma.octo.assignement.Services.JwtService;
import ma.octo.assignement.domain.Role;
import ma.octo.assignement.domain.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class UserController {
    private final RoleServiceImpl roleService;
    private final UtilisateurServiceImpl utilisateurService;

    private final JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
    @PostMapping("/registerNewUser")
    public ResponseEntity<Utilisateur> registerUser(@RequestBody Utilisateur user){
        return ResponseEntity.ok().body(utilisateurService.registerNewUser(user));
    }
}

