package com.gmail.ykomarnytskyi2022.CartaLingua.service.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public record CreateFlashCardDto(
        @NotNull @NotBlank @Min(value = 1) @Max(value = 100) String word,
        @NotNull @NotBlank @Min(value = 1) @Max(value = 100) String translation,
        Optional<String> transcription,
        LocalDate creationDate) {

    public CreateFlashCardDto {
        if (transcription.isPresent()) {
           transcription = transcription.filter(s -> !s.isBlank() || !s.isEmpty());
        }
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
    }
}
