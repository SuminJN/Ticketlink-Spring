package com.hello.ticketlink.musical.service;

import com.hello.ticketlink.dto.MusicalCreateRequestDto;
import com.hello.ticketlink.dto.MusicalUpdateRequestDto;
import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.musical.repository.MusicalRepository;
import com.hello.ticketlink.util.FileUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalService {

    private final MusicalRepository musicalRepository;

    @Transactional
    public Long save(MusicalCreateRequestDto requestDto, MultipartFile image, HttpServletRequest request) {
        Musical musical = Musical.builder()
                .title(requestDto.title())
                .genre(requestDto.genre())
                .description(requestDto.description())
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .runningTime(requestDto.runningTime())
                .image(FileUtil.uploadFile(image, "", request))
                .build();

        return musicalRepository.save(musical).getId();
    }

//    @Transactional(readOnly = true) // TODO Pageable 기능 추가 예정
//    public Page<Musical> findAllMusicals(Pageable pageable) {
//        return musicalRepository.findAll(pageable);
//    }

    public List<Musical> findAllMusicals() {
        return musicalRepository.findAll();
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
    public void deleteMusical(Long musicalId) {
        Musical musical = musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));

        // 실제로 삭제는 되지 않고 공연 종료 처리
        if (musical.isDeleted()) throw new EntityNotFoundException("이미 삭제된 뮤지컬입니다.");
        musical.deleteMusical();

//        musicalRepository.delete(musical);

    }
}
