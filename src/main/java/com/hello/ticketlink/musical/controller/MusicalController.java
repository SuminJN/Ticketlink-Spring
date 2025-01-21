package com.hello.ticketlink.musical.controller;

import com.hello.ticketlink.dto.MusicalCreateRequestDto;
import com.hello.ticketlink.dto.MusicalUpdateRequestDto;
import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.musical.service.MusicalService;
import com.hello.ticketlink.poster.PosterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/musicals")
public class MusicalController {

    private final MusicalService musicalService;
    private final PosterService posterService;

    @GetMapping // 뮤지컬 목록 페이지
    public String getAllMusicals(Model model) {
        List<Musical> musicals = musicalService.findAllMusicals();
        model.addAttribute("musicals", musicals);
        return "list";
    }

    @GetMapping("/{musicalId}") // 뮤지컬 상세 페이지
    public String getOneMusical(@PathVariable Long musicalId, Model model) {
        Musical musical = musicalService.findMusicalById(musicalId);
        int seatCount = musical.availableTicket().size();
        model.addAttribute("seatCount", seatCount);
        model.addAttribute("musical", musical);
        return "musical";
    }

    @GetMapping("/add") // 뮤지컬 추가 페이지
    public String addForm() {
        return "add";
    }

    @PostMapping("/add") // 뮤지컬 추가 요청
    public String addMusical(MusicalCreateRequestDto requestDto,
                             @RequestParam MultipartFile image,
                             RedirectAttributes redirectAttributes) {

        Long musicalId = musicalService.save(requestDto, image);

        redirectAttributes.addAttribute("musicalId", musicalId);
        return "redirect:/musicals/{musicalId}";
    }

    @GetMapping("/{musicalId}/edit") // 뮤지컬 수정 페이지
    public String editForm(@PathVariable Long musicalId, Model model) {
        Musical musical = musicalService.findMusicalById(musicalId);
        model.addAttribute("musical", musical);
        return "edit";
    }

    @PostMapping("/{musicalId}/edit") // 뮤지컬 수정 요청
    public String editMusical(@PathVariable Long musicalId,
                              @ModelAttribute MusicalUpdateRequestDto requestDTO,
                              @RequestParam MultipartFile image) {
        musicalService.updateMusical(musicalId, requestDTO);

        if(!image.isEmpty()) musicalService.updatePoster(musicalId, image);

        return "redirect:/musicals/{musicalId}";
    }

    @GetMapping("/{musicalId}/delete") // 뮤지컬 삭제 요청
    public String deleteMusical(@PathVariable Long musicalId) {
        musicalService.deleteMusical(musicalId);
        return "redirect:/musicals";
    }
}











