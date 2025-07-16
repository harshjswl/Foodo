package com.food_Order_application.backend.Controller;

import com.food_Order_application.backend.Entity.Hotel;
import com.food_Order_application.backend.Entity.HotelMenu;
import com.food_Order_application.backend.Service.HotelMenuService;
import com.food_Order_application.backend.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels/{email}/menu")
public class HotelMenuController {

    @Autowired
    private HotelMenuService menuService;

    @Autowired
    private HotelService hotelService;

    /**
     * Add a new dish to a hotel's menu
     */
    @PostMapping
    public ResponseEntity<HotelMenu> addDish(@PathVariable String email,
                                             @RequestBody HotelMenu menu) {
        Hotel hotel = hotelService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hotel not found with email: " + email));
        menu.setHotel(hotel);
        HotelMenu saved = menuService.addDish(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Get all dishes for a hotel's menu
     */
    @GetMapping
    public ResponseEntity<List<HotelMenu>> getMenu(@PathVariable String email) {
        Hotel hotel = hotelService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Hotel not found with email: " + email));
        List<HotelMenu> menuList = menuService.getHotelMenu(hotel);
        return ResponseEntity.ok(menuList);
    }

    /**
     * Get a specific dish by its ID
     */
    @GetMapping("/{dishId}")
    public ResponseEntity<HotelMenu> getDish(@PathVariable String email,
                                             @PathVariable Long dishId) {
        HotelMenu dish = menuService.getDishById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + dishId));
        return ResponseEntity.ok(dish);
    }

    /**
     * Update an existing dish
     */
    @PutMapping("/{dishId}")
    public ResponseEntity<HotelMenu> updateDish(@PathVariable String email,
                                                @PathVariable Long dishId,
                                                @RequestBody HotelMenu menu) {
        // Ensure dish exists
        HotelMenu existing = menuService.getDishById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + dishId));
        // Optionally, verify that the dish belongs to the hotel
        if (!existing.getHotel().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        menu.setId(dishId);
        menu.setHotel(existing.getHotel());
        HotelMenu updated = menuService.updateDish(menu);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a dish from the menu
     */
    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable String email,
                                           @PathVariable Long dishId) {
        HotelMenu existing = menuService.getDishById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + dishId));
        if (!existing.getHotel().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        menuService.deleteDish(existing);
        return ResponseEntity.noContent().build();
    }
}
