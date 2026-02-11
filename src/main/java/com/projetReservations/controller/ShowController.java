package com.projetReservations.controller;

import com.projetReservations.model.Show;
import com.projetReservations.repository.ShowRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

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
        if (show == null) return "redirect:/shows";

        model.addAttribute("show", show);
        model.addAttribute("title", show.getTitle());
        return "show/show";
    }

    // GET /shows/create
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("show", new Show());
        model.addAttribute("title", "Créer un show");
        return "show/create";
    }

    // POST /shows
    @PostMapping
    public String store(@Valid @ModelAttribute Show show,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Créer un show");
            return "show/create";
        }

        showRepository.save(show);
        return "redirect:/shows";
    }
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Show show,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Modifier un show");
            return "show/edit";
        }

        // Sécurité : forcer l'id venant de l'URL
        show.setId(id);

        showRepository.save(show);
        return "redirect:/shows/" + id;
    }
    // GET /shows/{id}/edit
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Show show = showRepository.findById(id).orElse(null);
        if (show == null) return "redirect:/shows";

        model.addAttribute("show", show);
        model.addAttribute("title", "Modifier un show");
        return "show/edit";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (showRepository.existsById(id)) {
            showRepository.deleteById(id);
        }
        return "redirect:/shows";
    }
}