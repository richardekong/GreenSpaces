package com.daveace.greenspaces.lightweight;


import com.daveace.greenspaces.role.Role;
import com.daveace.greenspaces.role.RoleRepo;
import com.daveace.greenspaces.role.RoleService;
import com.daveace.greenspaces.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {


    @Mock
    RoleRepo repo;

    @InjectMocks
    RoleService roleService;

    private Role createRole(){
        return Role.builder()
                .id(1)
                .role("ADMIN")
                .user(new User())
                .build();
    }

    @Test
    @DisplayName("Verify that a role can be saved")
    void verifyThatARoleCanBeSaved(){
        Role role = createRole();
        when(repo.save(role)).thenReturn(role);
        roleService.save(role);
        verify(repo, times(1)).existsById(role.getId());
        verify(repo, times(1)).save(role);
    }

    @Test
    @DisplayName("Verify that roles can be saved")
    void verifyThatRolesCanBeSaved(){
        Set<Role> roles = Set.of(createRole());
        when(repo.saveAll(roles)).thenReturn(new ArrayList<>(roles));
        roleService.saveAll(roles);
        verify(repo, times(1)).saveAll(roles);
    }

    @Test
    @DisplayName("Verify that a role can be read")
    void verifyThatARoleCanBeRead(){
        Role role = createRole();
        when(repo.findById(role.getId())).thenReturn(Optional.of(role));
        Role saveRole = roleService.findById(role.getId());
        verify(repo, times(1)).findById(saveRole.getId());
    }

    @Test
    @DisplayName("Verify that all roles can be read")
    void verifyThatAllRolesCanBeRead(){
        List<Role> roles = List.of(createRole());
        when(repo.findAll()).thenReturn(roles);
        roleService.findAll();
        assertThat(roles).size().isGreaterThan(0);
        verify(repo, times(1)).findAll();

    }

    @Test
    @DisplayName("Verify that roles can be updated")
    void verifyThatRolesCanBeUpdated(){
        Role role = createRole();
        Function<Role,Role> ops = r-> r.setRole("MANAGER");
        when(repo.findById(role.getId())).thenReturn(Optional.of(role));
        roleService.update(role.getId(), ops);
        verify(repo, times(1)).findById(role.getId());
        verify(repo, times(1)).save(role);
    }

    @Test
    @DisplayName("Verify that role can be deleted")
    void verifyThatRoleCanBeDeleted(){
        Role role = createRole();
        when(repo.existsById(role.getId())).thenReturn(true);
        doNothing().when(repo).deleteById(role.getId());
        roleService.delete(role.getId());
        verify(repo, times(1)).existsById(role.getId());
        verify(repo, times(1)).deleteById(role.getId());
    }




}
