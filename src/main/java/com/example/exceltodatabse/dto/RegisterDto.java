package com.example.exceltodatabse.dto;

import lombok.Data;


public record RegisterDto(
        String username,
        String password,
        String email
) {


}
