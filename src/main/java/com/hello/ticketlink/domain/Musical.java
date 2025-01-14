package com.hello.ticketlink.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @NotBlank
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public void deleteMusical() {
        this.isDeleted = true;
    }

    @Builder
    public Musical(String title, String genre, String description,
                   LocalDate startDate, LocalDate endDate, int runningTime,
                   String image) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runningTime = runningTime;
        this.image = image;
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
}
