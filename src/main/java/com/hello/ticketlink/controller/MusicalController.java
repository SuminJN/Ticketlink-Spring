package com.hello.ticketlink.controller;

import com.hello.ticketlink.domain.Musical;
import com.hello.ticketlink.domain.dto.MusicalUpdateRequestDTO;
import com.hello.ticketlink.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/musicals")
public class MusicalController {

    private final MusicalService musicalService;

    @GetMapping // 뮤지컬 목록 페이지
    public String getAllMusicals(Model model) {
        List<Musical> musicals = musicalService.findAllMusicals();
        model.addAttribute("musicals", musicals);
        return "list";
    }

    @GetMapping("/{musicalId}") // 뮤지컬 상세 페이지
    public String getOneMusical(@PathVariable Long musicalId, Model model) {
        Musical musical = musicalService.findMusicalById(musicalId);
        model.addAttribute("musical", musical);
        return "musical";
    }
    
    @GetMapping("/add") // 뮤지컬 추가 페이지
    public String addForm() {
        return "add";
    }

    @PostMapping("/add") // 뮤지컬 추가 요청
    public String addMusical(@ModelAttribute Musical musical, RedirectAttributes redirectAttributes) {
        Long musicalId = musicalService.save(musical);
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
    public String editMusical(@PathVariable Long musicalId, @ModelAttribute MusicalUpdateRequestDTO requestDTO) {
        musicalService.updateMusical(musicalId, requestDTO);
        return "redirect:/musicals/{musicalId}";
    }
    
    @GetMapping("/{musicalId}/delete") // 뮤지컬 삭제 요청
    public String deleteMusical(@PathVariable Long musicalId) {
        musicalService.deleteMusical(musicalId);
        return "redirect:/musicals";
    }
}











