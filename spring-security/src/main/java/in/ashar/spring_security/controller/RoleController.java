package in.ashar.spring_security.controller;

import in.ashar.spring_security.dto.RoleDto;
import in.ashar.spring_security.dto.UpdateRoleDto;
import in.ashar.spring_security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok(roleService.createRole(roleDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@RequestBody RoleDto roleDto){
        roleService.deleteRole(roleDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody UpdateRoleDto roleDto){
        return ResponseEntity.ok(roleService.updateRole(roleDto));
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

}
