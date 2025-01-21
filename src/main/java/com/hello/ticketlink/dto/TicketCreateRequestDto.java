package com.hello.ticketlink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record TicketCreateRequestDto(
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotBlank int seatNumber
) {

}
