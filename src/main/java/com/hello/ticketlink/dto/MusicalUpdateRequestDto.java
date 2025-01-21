package com.hello.ticketlink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record MusicalUpdateRequestDto(
        @NotBlank String title,
        @NotBlank String genre,
        @NotBlank String description,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        @NotNull @Positive int runningTime
) {

}
