package com.hello.ticketlink.service;

import com.hello.ticketlink.domain.Musical;
import com.hello.ticketlink.dto.MusicalUpdateRequestDTO;
import com.hello.ticketlink.repository.MusicalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicalService {

    private final MusicalRepository musicalRepository;

    @Transactional
    public Long save(Musical musical) {
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
    public void updateMusical(Long musicalId, MusicalUpdateRequestDTO requestDTO) {
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

        if (musical.isDeleted()) {
            throw new EntityNotFoundException("이미 삭제된 뮤지컬입니다.");
        }

        musical.deleteMusical();
    }
}
