package com.hello.ticketlink.musical.service;

import com.hello.ticketlink.dto.MusicalCreateRequestDto;
import com.hello.ticketlink.dto.MusicalUpdateRequestDto;
import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.musical.repository.MusicalRepository;
import com.hello.ticketlink.poster.Poster;
import com.hello.ticketlink.poster.PosterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalService {

    private final MusicalRepository musicalRepository;
    private final PosterService posterService;


    @Transactional
    public Long save(MusicalCreateRequestDto requestDto, MultipartFile image) {

        Poster poster = posterService.uploadThumbnail(image);

        Musical musical = Musical.builder()
                .title(requestDto.title())
                .genre(requestDto.genre())
                .description(requestDto.description())
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .runningTime(requestDto.runningTime())
                .poster(poster)
                .build();

        return musicalRepository.save(musical).getId();
    }

    public Page<Musical> findAllMusicals(Pageable pageable) {
        return musicalRepository.findAll(pageable);
    }

    public Musical findMusicalById(Long musicalId) {
        return musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));
    }

    @Transactional
    public void updateMusical(Long musicalId, MusicalUpdateRequestDto requestDTO) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));

        musical.updateMusical(
                requestDTO.title(),
                requestDTO.genre(),
                requestDTO.description(),
                requestDTO.startDate(),
                requestDTO.endDate(),
                requestDTO.runningTime()
        );
    }

    @Transactional
    public void updatePoster(Long musicalId, MultipartFile image) {
        Poster poster = posterService.uploadThumbnail(image);
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));
        musical.updatePoster(poster);
    }

    @Transactional
    public void deleteMusical(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));

        // 실제로 삭제는 되지 않고 공연 종료 처리
        if (musical.isDeleted()) throw new EntityNotFoundException("이미 삭제된 뮤지컬입니다.");
        musical.deleteMusical();

//        musicalRepository.delete(musical);

    }
}
