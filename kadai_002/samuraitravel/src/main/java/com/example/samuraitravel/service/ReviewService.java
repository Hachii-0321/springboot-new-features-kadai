package com.example.samuraitravel.service;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void create(ReviewRegisterForm reviewRegisterForm, User user, House house) {
        Review review = new Review();
        review.setHouse(house);
        review.setUser(user);
        review.setRating(reviewRegisterForm.getReviewRating());
        review.setReviewContent(reviewRegisterForm.getReviewContent());

        reviewRepository.save(review);
    }

    public void update (ReviewEditForm reviewEditForm) {
        Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
        review.setRating(reviewEditForm.getReviewRating());
        review.setReviewContent(reviewEditForm.getReviewContent());

        reviewRepository.save(review);
    }
}