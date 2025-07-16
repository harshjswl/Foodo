package com.food_Order_application.backend.Service;


import com.food_Order_application.backend.DTO.HotelRegistrationDTO;
import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel registerHotel(HotelRegistrationDTO hotelDTO);
    Optional<Hotel> findByEmail(String email);
    Optional<Hotel> findByPhoneNumber(String number);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String number);
    List<HotelMenu> getHotelMenuByEmail(String email);
    Hotel updateHotelDetails(String email, Hotel hotel);
    void deleteHotelById(Long id);
    List<Hotel> getAllHotel();
}
