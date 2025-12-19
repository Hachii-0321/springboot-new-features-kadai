package com.example.samuraitravel.repository;
import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite,Integer> {
    public Page<Favorite> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    public Favorite findByHouseAndUser(House house, User user);
    public boolean existsByHouseAndUser(House house, User user);
}
