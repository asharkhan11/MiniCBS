package in.ashar.spring_security.service;

import in.ashar.spring_security.dto.RoleDto;
import in.ashar.spring_security.dto.UpdateRoleDto;
import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.exception.AlreadyExistsException;
import in.ashar.spring_security.exception.NotFoundException;
import in.ashar.spring_security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RolesRepository rolesRepository;

    public RoleDto createRole(RoleDto roleDto) {

        roleDto.setRoleName(roleDto.getRoleName().toUpperCase());

        if(!rolesRepository.existsByRole(roleDto.getRoleName())){
            Roles role = new Roles();
            role.setRole(roleDto.getRoleName());
            rolesRepository.save(role);
            return roleDto;
        }

        throw new AlreadyExistsException("role with name : "+ roleDto.getRoleName() + " already exists");

    }

    public void deleteRole(RoleDto roleDto) {
        rolesRepository.deleteByRole(roleDto.getRoleName().toUpperCase());
    }

    public RoleDto updateRole(UpdateRoleDto roleDto){
        Roles existing = rolesRepository.findByRole(roleDto.getOldRoleName().toUpperCase()).orElseThrow(() -> new NotFoundException("Role with name: " + roleDto.getOldRoleName().toUpperCase() + " not found"));
        existing.setRole(roleDto.getNewRoleName().toUpperCase());
        rolesRepository.save(existing);
        return new RoleDto(roleDto.getNewRoleName().toUpperCase());
    }

    public List<RoleDto> getAllRoles() {
        List<Roles> roles = rolesRepository.findAll();
        return roles.stream().map(Roles::getRole).map(RoleDto::new).toList();
    }

    public Roles getRoleByName(String roleName) {

        return rolesRepository.findByRole(roleName).orElseThrow(()-> new NotFoundException("Role not found : "+ roleName));

    }
}
