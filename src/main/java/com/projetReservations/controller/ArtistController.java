package com.projetReservations.controller;

import com.projetReservations.service.ArtistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.projetReservations.model.Artist;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.projetReservations.model.Artist;
import org.springframework.web.bind.annotation.DeleteMapping;




@Controller
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("artists", artistService.findAll());
        return "artist/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        var artistOpt = artistService.findById(id);
        if (artistOpt.isEmpty()) {
            return "redirect:/artists";
        }
        model.addAttribute("artist", artistOpt.get());
        return "artist/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var artistOpt = artistService.findById(id);
        if (artistOpt.isEmpty()) {
            return "redirect:/artists";
        }
        model.addAttribute("artist", artistOpt.get());
        return "artist/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("artist") Artist formArtist,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("artist", formArtist);
            return "artist/edit";
        }

        var artistOpt = artistService.findById(id);
        if (artistOpt.isEmpty()) {
            return "redirect:/artists";
        }

        Artist artist = artistOpt.get();
        artist.setFirstname(formArtist.getFirstname());
        artist.setLastname(formArtist.getLastname());
        artistService.save(artist);

        return "redirect:/artists/" + id;
    }
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("artist", new Artist("", ""));
        return "artist/create";
    }

    @PostMapping
    public String store(@Valid @ModelAttribute("artist") Artist artist,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "artist/create";
        }

        Artist saved = artistService.save(artist);
        return "redirect:/artists/" + saved.getId();
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        artistService.deleteById(id);
        return "redirect:/artists";
    }



}
