package com.food_Order_application.backend.Service.Impl;


import com.food_Order_application.backend.DTO.HotelRegistrationDTO;
import com.food_Order_application.backend.Repository.HotelMenuRepository;
import com.food_Order_application.backend.Repository.HotelRepository;
import com.food_Order_application.backend.Service.HotelService;
import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HotelMenuRepository hotelMenuRepository;

    @Override
    @Transactional
    public Hotel registerHotel(HotelRegistrationDTO hotelDTO) {
        if (hotelRepository.existsByEmail(hotelDTO.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }
        if (hotelRepository.existsByPhoneNumber(hotelDTO.getPhoneNumber())) {
            throw new RuntimeException("Phone number is already in use.");
        }
        Hotel hotel = Hotel.builder()
                .username(hotelDTO.getUsername())
                .email(hotelDTO.getEmail())
                .phoneNumber(hotelDTO.getPhoneNumber())
                .dietaryClassification(hotelDTO.getDietaryClassification())
                .passwordHash(passwordEncoder.encode(hotelDTO.getPassword()))
                .hotelName(hotelDTO.getHotelName())
                .location(hotelDTO.getLocation())
                .openingTime(hotelDTO.getOpeningTime())
                .closingTime(hotelDTO.getClosingTime())
                .build();
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> findByEmail(String email) {
        return hotelRepository.findByEmail(email);
    }

    @Override
    public Optional<Hotel> findByPhoneNumber(String number) {
        return hotelRepository.findByPhoneNumber(number);
    }

    @Override
    public boolean existsByEmail(String email) {
        return hotelRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String number) {
        return hotelRepository.existsByPhoneNumber(number);
    }

    @Override
    public List<HotelMenu> getHotelMenuByEmail(String email) {
        Hotel hotel = hotelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hotel not found with email: " + email));
        return hotelMenuRepository.findByHotel(hotel);
    }

    @Override
    @Transactional
    public Hotel updateHotelDetails(String email, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hotel with email " + email + " not found."));
        if (!existingHotel.getEmail().equals(hotel.getEmail()) && hotelRepository.existsByEmail(hotel.getEmail())) {
            throw new RuntimeException("New email is already in use by another hotel.");
        }
        if (!existingHotel.getPhoneNumber().equals(hotel.getPhoneNumber()) &&
                hotelRepository.existsByPhoneNumber(hotel.getPhoneNumber())) {
            throw new RuntimeException("Phone number is already in use by another hotel.");
        }
        existingHotel.setUsername(hotel.getUsername());
        existingHotel.setEmail(hotel.getEmail());
        existingHotel.setPhoneNumber(hotel.getPhoneNumber());
        existingHotel.setDietaryClassification(hotel.getDietaryClassification());
        existingHotel.setHotelName(hotel.getHotelName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setOpeningTime(hotel.getOpeningTime());
        existingHotel.setClosingTime(hotel.getClosingTime());
        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteHotelById(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }
}