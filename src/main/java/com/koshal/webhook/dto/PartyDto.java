package com.koshal.webhook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PartyDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @Size(min = 2, max = 3)
    private String country;

    private String email;
}
