package com.food_Order_application.backend.Controller;

import com.food_Order_application.backend.DTO.HotelLoginDTO;
import com.food_Order_application.backend.DTO.HotelRegistrationDTO;
import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Repository.HotelRepository;
import com.food_Order_application.backend.Security.JwtUtil;
import com.food_Order_application.backend.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@RestController
@RequestMapping("/api/users")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody HotelRegistrationDTO dto) {
        hotelService.registerHotel(dto);
        return ResponseEntity.ok("Hotel registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody HotelLoginDTO dto) {
        Hotel hotel = hotelRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(dto.getPassword(), hotel.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(hotel.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}