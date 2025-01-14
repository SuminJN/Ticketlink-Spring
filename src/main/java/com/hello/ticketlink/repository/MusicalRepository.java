package com.hello.ticketlink.repository;

import com.hello.ticketlink.domain.Musical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicalRepository extends JpaRepository<Musical, Long> {
}
