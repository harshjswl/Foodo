package com.food_Order_application.backend.Repository;


import com.food_Order_application.backend.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByEmail(String email);
    Optional<Hotel> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}