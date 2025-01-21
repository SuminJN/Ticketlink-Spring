package com.hello.ticketlink.ticket.controller;

import com.hello.ticketlink.dto.TicketCreateRequestDto;
import com.hello.ticketlink.musical.domain.Musical;
import com.hello.ticketlink.musical.service.MusicalService;
import com.hello.ticketlink.ticket.domain.Ticket;
import com.hello.ticketlink.ticket.service.TicketService;
import com.hello.ticketlink.user.domain.User;
import com.hello.ticketlink.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final MusicalService musicalService;
    private final UserService userService;

    @PostMapping("/musicals/{musicalId}/ticket")
    public String ticketing(@PathVariable Long musicalId,
                            @ModelAttribute TicketCreateRequestDto requestDto,
                            Principal principal) {
        User user = userService.getUser(principal.getName());
        Musical musical = musicalService.findMusicalById(musicalId);

        ticketService.create(user, musical, requestDto);

        return "redirect:/reservations";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, Principal principal) {
        User user = userService.getUser(principal.getName());
        List<Ticket> tickets = ticketService.findByUserId(user.getId());
        model.addAttribute("tickets", tickets);

        return "reservations";
    }
}
