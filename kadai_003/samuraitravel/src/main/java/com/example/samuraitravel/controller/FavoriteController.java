package com.example.samuraitravel.controller;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {
    private UserRepository userRepository;
    private FavoriteRepository favoriteRepository;
    private HouseRepository houseRepository;
    private FavoriteService favoriteService;

    public FavoriteController(UserRepository userRepository, FavoriteRepository favoriteRepository, HouseRepository houseRepository, FavoriteService favoriteservice) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.houseRepository = houseRepository;
        this.favoriteService = favoriteservice;
    }

    @GetMapping
    public String show(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page=0, size = 10, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
        Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        model.addAttribute("favoritePage",favoritePage);
        return "favorites/index";
    }

    @PostMapping("create/{id}")
    public String create(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id){
        House house = houseRepository.getReferenceById(id);
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
        favoriteService.create(house, user);
        return "redirect:/houses/{id}";
    }

    @PostMapping("delete/{id}")
    public String delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable(name = "id") Integer id){
        House house = houseRepository.getReferenceById(id);
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
        Favorite favorite = favoriteRepository.findByHouseAndUser(house, user);
        favoriteRepository.delete(favorite);
        return "redirect:/houses/{id}";
    }

}
