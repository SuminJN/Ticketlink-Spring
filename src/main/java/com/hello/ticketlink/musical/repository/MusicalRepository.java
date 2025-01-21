package com.hello.ticketlink.musical.repository;

import com.hello.ticketlink.musical.domain.Musical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalRepository extends JpaRepository<Musical, Long> {
}
