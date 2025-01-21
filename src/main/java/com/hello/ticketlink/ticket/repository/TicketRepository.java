package com.hello.ticketlink.ticket.repository;

import com.hello.ticketlink.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser_Id(Long userId);
}
