package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public record FlashCardDto(
        @NotNull @NotBlank String id,
        @NotNull @NotBlank @Min(value = 1) @Max(value = 100) String word,
        @NotNull @NotBlank @Min(value = 1) @Max(value = 100) String translation,
        Optional<String> transcription,
        @NotNull LocalDate creationDate
) {
}
