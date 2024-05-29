package com.example.exceltodatabse.controller;

import com.example.exceltodatabse.dto.LoginDto;
import com.example.exceltodatabse.dto.RegisterDto;
import com.example.exceltodatabse.dto.web.JwtResponse;
import com.example.exceltodatabse.dto.web.UserDto;
import com.example.exceltodatabse.entity.Role;
import com.example.exceltodatabse.entity.UserEntity;
import com.example.exceltodatabse.exception.UserNotFoundException;
import com.example.exceltodatabse.provider.JwtProvider;
import com.example.exceltodatabse.repository.RoleRepository;
import com.example.exceltodatabse.repository.UserRepository;
import com.example.exceltodatabse.service.UserService;
import com.example.exceltodatabse.service.imp.UserServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserServiceImp userServiceImp;

    public AuthController(UserServiceImp userServiceImp, JwtProvider jwtProvider, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userServiceImp = userServiceImp;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(userServiceImp.login(dto));
    }



    @Operation(
            summary = "register a new user",
            description = "This endpoint register a new user with a provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        userServiceImp.register(registerDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id){
        boolean checker = userServiceImp.delete(id);
        if(checker){
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> update(@RequestBody UserDto dto){
        boolean checker = userServiceImp.update(dto);
        if(checker){
            return new ResponseEntity<>("User updated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }


}
