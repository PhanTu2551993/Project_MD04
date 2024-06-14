package ra.project_md04.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.project_md04.constans.RoleName;
import ra.project_md04.model.entity.Roles;
import ra.project_md04.repository.IRoleRepository;
import ra.project_md04.service.IRoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    @Override
    public Roles findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("role not found"));
    }
}

