package com.koshal.webhook.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class PartyDto {

    @NotBlank private String id;
    @NotBlank private String name;
    @Email private String email;
    @Size(min = 2, max = 2) private String country;
}
