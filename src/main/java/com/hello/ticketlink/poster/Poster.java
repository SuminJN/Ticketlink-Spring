package com.hello.ticketlink.poster;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "poster")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "image_path", nullable = false)
    private String imagePath; // 사진 경로

    @Builder
    public Poster(String imagePath) {
        this.imagePath = imagePath;
    }
}
