package com.hello.ticketlink.service;

import com.hello.ticketlink.domain.Musical;
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
    public Musical save(Musical musical) {
        return musicalRepository.save(musical);
    }

//    @Transactional(readOnly = true) // TODO Pageable 기능 추가 예정
//    public Page<Musical> findAllMusicals(Pageable pageable) {
//        return musicalRepository.findAll(pageable);
//    }

    @Transactional(readOnly = true)
    public List<Musical> findAllMusicals() {
        return musicalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Musical findMusicalById(Long musicalId) {

        return musicalRepository.findById(musicalId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 뮤지컬입니다."));
    }

    @Transactional
    public void deleteMusical(Musical musical) {

        if (musical.isDeleted()) {
            throw new EntityNotFoundException("이미 삭제된 뮤지컬입니다.");
        }

        musical.deleteMusical();
    }
}
