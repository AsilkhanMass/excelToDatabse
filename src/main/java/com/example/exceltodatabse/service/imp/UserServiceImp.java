package com.example.exceltodatabse.service.imp;

import com.example.exceltodatabse.dto.LoginDto;
import com.example.exceltodatabse.dto.RegisterDto;
import com.example.exceltodatabse.dto.web.JwtResponse;
import com.example.exceltodatabse.dto.web.UserDto;
import com.example.exceltodatabse.entity.Role;
import com.example.exceltodatabse.entity.UserEntity;
import com.example.exceltodatabse.exception.UserAlreadyExistsException;
import com.example.exceltodatabse.exception.UserNotFoundException;
import com.example.exceltodatabse.provider.JwtProvider;
import com.example.exceltodatabse.repository.RoleRepository;
import com.example.exceltodatabse.repository.UserRepository;
import com.example.exceltodatabse.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;

    public UserServiceImp(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegisterDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException("User already exists by email: " + dto.email());
        }

        UserEntity user = UserEntity.builder()
                .email(dto.email())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginDto dto) {

        final UserEntity user = userRepository.findByEmail(dto.email())
                .orElseThrow(
                        () -> new UserNotFoundException("User not found by email: " + dto.email())
                );
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new JwtResponse(jwtProvider.generateToken(user), jwtProvider.getExpiration());
    }

    @Override
    public boolean delete(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Not found with id: " + id)
        );
        if(user==null){
            return false;
        }
        userRepository.delete(user);
        return true;

    }

    @Override
    public boolean update(UserDto dto) {
        UserEntity user = userRepository.findByEmail(dto.email()).orElseThrow(
                () -> new UserNotFoundException("Not found with email" + dto.email())
        );
        if(user==null){
            return false;
        }
        if(dto.email()!=null){
            user.setEmail(dto.email());
        }
        if(dto.username()!=null){
            user.setUsername(dto.username());
        }
        userRepository.save(user);
        return true;

    }

}
