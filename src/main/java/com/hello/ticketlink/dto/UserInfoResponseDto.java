package com.hello.ticketlink.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        @NotBlank String username,
        @Email String email
) {
}
