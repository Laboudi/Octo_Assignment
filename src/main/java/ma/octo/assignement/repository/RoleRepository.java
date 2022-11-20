package ma.octo.assignement.repository;



import ma.octo.assignement.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findRoleByRoleName(String rolename);
}
