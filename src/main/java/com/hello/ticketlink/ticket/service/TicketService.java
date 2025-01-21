package com.hello.ticketlink.ticket.service;

import com.hello.ticketlink.dto.TicketCreateRequestDto;
import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.ticket.domain.Ticket;
import com.hello.ticketlink.ticket.repository.TicketRepository;
import com.hello.ticketlink.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public void create(User user, Musical musical, TicketCreateRequestDto requestDto) {
        Ticket ticket = Ticket.builder()
                .user(user)
                .musical(musical)
                .date(requestDto.date())
                .seatNumber(requestDto.seatNumber())
                .build();

        ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<Ticket> findByUserId(Long userId) {
        return ticketRepository.findByUser_Id(userId);
    }
}
