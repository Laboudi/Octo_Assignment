package ma.octo.assignement.ServiceImpl;

import lombok.RequiredArgsConstructor;
import ma.octo.assignement.Services.RoleService;
import ma.octo.assignement.domain.Role;
import ma.octo.assignement.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
