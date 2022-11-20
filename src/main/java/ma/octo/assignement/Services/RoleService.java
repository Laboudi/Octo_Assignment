package ma.octo.assignement.Services;

import ma.octo.assignement.domain.Role;

import java.util.List;

public interface RoleService {
    public Role saveRole(Role role);
    public List<Role> getRoles();
}
