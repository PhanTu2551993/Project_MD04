package ra.project_md04.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md04.constans.RoleName;
import ra.project_md04.model.entity.Roles;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(RoleName roleName);
}
