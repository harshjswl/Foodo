package com.food_Order_application.backend.DTO;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class HotelRegistrationDTO {
    private String username;
    private String email;
    private String phoneNumber;
    private String dietaryClassification;
    // raw password from client
    private String password;
    private String hotelName;
    private String location;
    private String openingTime;
    private String closingTime;
}
