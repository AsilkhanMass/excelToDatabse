package com.example.exceltodatabse.service;

import com.example.exceltodatabse.dto.LoginDto;
import com.example.exceltodatabse.dto.RegisterDto;
import com.example.exceltodatabse.dto.web.JwtResponse;
import com.example.exceltodatabse.dto.web.UserDto;

import java.util.List;

public interface UserService {
    void register(RegisterDto dto);
    JwtResponse login(LoginDto dto);

    boolean delete(Long id);
    boolean update(UserDto dto);
}
