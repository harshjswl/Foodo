package com.food_Order_application.backend.Service;


import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;

import java.util.List;
import java.util.Optional;

public interface HotelMenuService {
    HotelMenu addDish(HotelMenu hotelMenu);
    List<HotelMenu> getHotelMenu(Hotel hotel);
    Optional<HotelMenu> getDishById(Long id);
    HotelMenu updateDish(HotelMenu hotelMenu);
    void deleteDish(HotelMenu hotelMenu);
}
