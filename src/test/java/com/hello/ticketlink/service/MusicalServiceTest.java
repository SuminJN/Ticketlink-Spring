package com.hello.ticketlink.service;

import com.hello.ticketlink.domain.Musical;
import com.hello.ticketlink.repository.MusicalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MusicalServiceTest {

    @Autowired
    private MusicalService musicalService;

    @Autowired
    private MusicalRepository musicalRepository;

    @Test
    @DisplayName("뮤지컬 전체 조회")
    void test1() {
        Musical musical = Musical.builder()
                .title("테스트 타이틀")
                .genre("코미디")
                .startDate(LocalDate.parse("2025-01-01"))
                .endDate(LocalDate.parse("2025-01-30"))
                .image("test")
                .description("test")
                .runningTime(100)
                .build();

        musicalService.save(musical);

        assertThat(musicalRepository.count()).isEqualTo(3L);
    }

}