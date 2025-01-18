package com.hello.ticketlink.repository;

import com.hello.ticketlink.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
