package com.example.exceltodatabse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Dto for user login")
public record LoginDto(
        @Schema(description = "User's email address", example = "something@gmail.com")
        String username,
        @Schema(description = "User's password", example = "abc1234")
        String password,
        String email
) {

}
