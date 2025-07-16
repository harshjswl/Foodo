package com.food_Order_application.backend.Repository;


import com.food_Order_application.backend.Entity.Hotel;

import com.food_Order_application.backend.Entity.HotelMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotelMenuRepository extends JpaRepository<HotelMenu, Long> {
    List<HotelMenu> findByHotel(Hotel hotel);
}