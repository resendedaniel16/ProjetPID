package com.projetReservations.controller;

import com.projetReservations.model.Show;
import com.projetReservations.repository.ShowRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    // GET /shows
    @GetMapping
    public String index(Model model) {
        model.addAttribute("shows", showRepository.findAll());
        model.addAttribute("title", "Shows");
        return "show/index";
    }

    // GET /shows/{id}
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Show show = showRepository.findById(id).orElse(null);

        if (show == null) {
            return "redirect:/shows";
        }

        model.addAttribute("show", show);
        model.addAttribute("title", show.getTitle());
        return "show/show";
    }
}