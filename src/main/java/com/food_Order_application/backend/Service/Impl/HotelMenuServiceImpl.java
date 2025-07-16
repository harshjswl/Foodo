package com.food_Order_application.backend.Service.Impl;


import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;
import com.food_Order_application.backend.Repository.HotelMenuRepository;
import com.food_Order_application.backend.Service.HotelMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HotelMenuServiceImpl implements HotelMenuService {
    @Autowired
    private HotelMenuRepository hotelMenuRepository;

    @Override
    @Transactional
    public HotelMenu addDish(HotelMenu hotelMenu) {
        return hotelMenuRepository.save(hotelMenu);
    }

    @Override
    public List<HotelMenu> getHotelMenu(Hotel hotel) {
        return hotelMenuRepository.findByHotel(hotel);
    }

    @Override
    public Optional<HotelMenu> getDishById(Long id) {
        return hotelMenuRepository.findById(id);
    }

    @Override
    @Transactional
    public HotelMenu updateDish(HotelMenu hotelMenu) {
        if (!hotelMenuRepository.existsById(hotelMenu.getId())) {
            throw new RuntimeException("Dish with ID " + hotelMenu.getId() + " not found.");
        }
        return hotelMenuRepository.save(hotelMenu);
    }

    @Override
    public void deleteDish(HotelMenu hotelMenu) {
        hotelMenuRepository.delete(hotelMenu);
    }
}
