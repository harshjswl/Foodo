package com.food_Order_application.backend.DTO;


import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class HotelLoginDTO {
    private String email;
    private String password;
}