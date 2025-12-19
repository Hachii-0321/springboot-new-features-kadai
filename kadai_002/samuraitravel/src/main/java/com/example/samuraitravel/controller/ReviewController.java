package com.example.samuraitravel.controller;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private HouseRepository houseRepository;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private ReviewService reviewService;

    public ReviewController(HouseRepository houseRepository, ReviewRepository reviewRepository, UserRepository userRepository, ReviewService reviewService) {
        this.houseRepository = houseRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        House house = houseRepository.getReferenceById(id);
        Page<Review> reviewPage = reviewRepository.findByHouseOrderByCreatedAtDesc(house, pageable);
        if (userDetailsImpl != null) {
            User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
            model.addAttribute("user", user);
        }
        model.addAttribute("house", house);
        model.addAttribute("reviewPage", reviewPage);
        return "reviews/show";
    }

    @GetMapping("/register/{id}")
    public String register(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);

        model.addAttribute("house", house);
        model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
        return "reviews/register";
    }

    @PostMapping("/create/{id}")
    public String create(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, @ModelAttribute ReviewRegisterForm reviewRegisterForm, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        User user = userRepository.getReferenceById(userDetailsImpl.getUser().getId());
        House house = houseRepository.getReferenceById(id);
        reviewService.create(reviewRegisterForm, user, house);

        redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Review review = reviewRepository.getReferenceById(id);
        House house = houseRepository.getReferenceById(review.getHouse().getId());
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(),review.getRating(),review.getReviewContent() );
        model.addAttribute("reviewEditForm", reviewEditForm);
        model.addAttribute("house", house);

        return "reviews/edit";
    }
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, @ModelAttribute ReviewEditForm reviewEditForm) {
        reviewService.update(reviewEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        reviewRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");

        return "redirect:/";
    }
}
