package com.food_Order_application.backend.Controller;

import com.food_Order_application.backend.DTO.HotelLoginDTO;
import com.food_Order_application.backend.DTO.HotelRegistrationDTO;
import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;
import com.food_Order_application.backend.Service.HotelService;
import com.food_Order_application.backend.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register a new hotel
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody HotelRegistrationDTO dto) {
        hotelService.registerHotel(dto);
        return ResponseEntity.ok("Hotel registered successfully!");
    }

    /**
     * Authenticate a hotel and return JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HotelLoginDTO dto) {
        Hotel hotel = hotelService.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), hotel.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials"));
        }
        String token = jwtUtil.generateToken(hotel.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    /**
     * Get hotel details by email
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<Hotel> getByEmail(@PathVariable String email) {
        return hotelService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get hotel details by phone number
     */
    @GetMapping("/by-phone/{phone}")
    public ResponseEntity<Hotel> getByPhone(@PathVariable("phone") String phoneNumber) {
        return hotelService.findByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get menu list for a hotel by its email
     */
    @GetMapping("/menu/{email}")
    public ResponseEntity<List<HotelMenu>> getMenuByEmail(@PathVariable String email) {
        List<HotelMenu> menu = hotelService.getHotelMenuByEmail(email);
        return ResponseEntity.ok(menu);
    }

    /**
     * Update hotel details by email
     */
    @PutMapping("/update/{email}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String email,
                                             @RequestBody HotelRegistrationDTO dto) {
        // Map DTO to entity
        Hotel update = Hotel.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .dietaryClassification(dto.getDietaryClassification())
                .hotelName(dto.getHotelName())
                .location(dto.getLocation())
                .openingTime(dto.getOpeningTime())
                .closingTime(dto.getClosingTime())
                .passwordHash(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null)
                .build();
        Hotel updated = hotelService.updateHotelDetails(email, update);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a hotel by its ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all hotels
     */
    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotel();
        return ResponseEntity.ok(hotels);
    }
}
