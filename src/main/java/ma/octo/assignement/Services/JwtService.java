package ma.octo.assignement.Services;


import lombok.RequiredArgsConstructor;
import ma.octo.assignement.RequestResponse.JwtRequest;
import ma.octo.assignement.RequestResponse.JwtResponse;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.jwt.JwtUtil;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService implements UserDetailsService {
    private final JwtUtil jwtUtil;
    private final UtilisateurRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName=jwtRequest.getUsername();
        String password=jwtRequest.getPassword();
        authenticate(userName,password);
        UserDetails userDetails=loadUserByUsername(userName);
        String newGeneratedToken=jwtUtil.generateToken(userDetails);
        Utilisateur user=userRepository.findByUsername(userName);
        return new JwtResponse(user,newGeneratedToken);
    }
    public void authenticate(String userName,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED",e);
        }catch (BadCredentialsException e){
            throw new Exception("Invalid_Credentials",e);
        }
    }

    private  Set getAuthority(Utilisateur user){
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user=userRepository.findByUsername(username);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthority(user)
            );
        }else {
            throw new UsernameNotFoundException("User not found with username : "+username);
        }
    }
}

