package com.usermanagement.user_management.Controller;

import com.usermanagement.user_management.Model.Role;
import com.usermanagement.user_management.Model.User;
import com.usermanagement.user_management.repo.RoleRepository;
import com.usermanagement.user_management.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AuthController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // ✅ Create/Register User (Admin Only)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Set<Role> roles = userDTO.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName())))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // ✅ Get All Users (Admin Only)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Get User by ID (Admin & Manager)
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // ✅ Update User (Admin Only)
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    // ✅ Delete User (Admin Only)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // ✅ Assign Roles to Users (Admin Only)
    @PostMapping("/users/{id}/roles")
    public ResponseEntity<User> assignRolesToUser(@PathVariable Long id, @RequestBody List<String> roleNames) {
        return ResponseEntity.ok(userService.assignRoles(id, roleNames));
    }
}
