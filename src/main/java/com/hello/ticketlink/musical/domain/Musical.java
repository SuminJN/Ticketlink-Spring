package com.hello.ticketlink.musical.domain;

import com.hello.ticketlink.poster.Poster;
import com.hello.ticketlink.ticket.domain.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "musical")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Musical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Column(name = "genre", nullable = false, length = 20)
    private String genre;

    @NotBlank
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Positive
    @Column(name = "running_time", nullable = false)
    private int runningTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_id", unique = true)
    private Poster poster;

    @OneToMany(mappedBy = "musical", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @Builder
    public Musical(String title, String genre, String description,
                   LocalDate startDate, LocalDate endDate, int runningTime,
                   Poster poster) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runningTime = runningTime;
        this.poster = poster;
    }

    public void updateMusical(String title, String genre, String description,
                              LocalDate startDate, LocalDate endDate, int runningTime) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runningTime = runningTime;
    }

    public void updatePoster(Poster poster) {
        this.poster = poster;
    }

    public int[] availableTicket() {
        Set<Integer> bookedTicketNumbers = tickets.stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toSet());

        // 1부터 30까지의 범위에서 예약되지 않은 티켓 번호만 반환
        return IntStream.rangeClosed(1, 30)
                .filter(ticket -> !bookedTicketNumbers.contains(ticket))
                .toArray();
    }
}
