package com.hello.ticketlink.ticket.domain;

import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ticket")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(min = 1, max = 30)
    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musical_id", referencedColumnName = "id", nullable = false)
    private Musical musical;

    @Builder
    public Ticket(User user, Musical musical, LocalDate date, int seatNumber) {
        Assert.notNull(user, "사용자가 존재하지 않습니다.");
        Assert.notNull(musical, "뮤지컬이 존재하지 않습니다.");
        Assert.notNull(seatNumber, "좌석이 존재하지 않습니다.");

        this.user = user;
        this.musical = musical;
        this.date = date;
        this.seatNumber = seatNumber;
    }

    public void setUser(User user) {
        Assert.notNull(user, "user cannot be null");

        if (Objects.nonNull(this.user)) {
            this.user.getTickets().remove(this);
        }
        this.user = user;
        user.getTickets().add(this);
    }

    public void setMusical(Musical musical) {
        Assert.notNull(musical, "musical cannot be null");

        if (Objects.nonNull(this.musical)) {
            this.musical.getTickets().remove(this);
        }
        this.musical = musical;
        musical.getTickets().add(this);
    }
}
