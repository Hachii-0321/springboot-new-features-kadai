package com.example.samuraitravel.repository;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    public Page<Review> findByHouseOrderByCreatedAtDesc(House house, Pageable pageable);
    public boolean existsByHouseAndUser(House house, User user);
    public List<Review> findTop6ByHouseOrderByCreatedAtDesc(House house);
}