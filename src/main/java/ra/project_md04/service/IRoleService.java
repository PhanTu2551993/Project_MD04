package ra.project_md04.service;

import ra.project_md04.constans.RoleName;
import ra.project_md04.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    List<Roles> getAllRoles();
    Roles findByRoleName(RoleName roleName);
}
