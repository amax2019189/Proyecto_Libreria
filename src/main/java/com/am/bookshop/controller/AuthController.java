package com.am.bookshop.controller;

import com.am.bookshop.entity.user.UserDto;
import com.am.bookshop.entity.user.UserModel;
import com.am.bookshop.service.UserService;
import com.am.bookshop.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        try {
            String token = userService.existsUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel user) {
        try {
            UserModel createdUser = userService.save(user);
            log.info("Usuario fue creado correctamente: {}", createdUser);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear el usuario: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/change-role/{id}")
    public ResponseEntity<UserModel> changeUserRole(@PathVariable String id, @RequestBody Role newRole) {
        try {
            UserModel updatedUser = userService.updateUserRole(id, newRole);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            log.error("Error al cambiar el rol del usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}