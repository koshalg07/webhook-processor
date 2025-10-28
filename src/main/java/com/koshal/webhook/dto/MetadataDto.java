package com.koshal.webhook.dto;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MetadataDto {
    private String reference;
    private String notes;
}
