package ra.project_md04.service;

import ra.project_md04.constans.RoleName;
import ra.project_md04.model.entity.Roles;

public interface IRoleService {
    Roles findByRoleName(RoleName roleName);
}
