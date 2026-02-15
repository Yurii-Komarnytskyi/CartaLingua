package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record CreateFlashCardDto(
        @NotNull CreateWordDto createWordDto,
        @NotNull @NotBlank @Min(value = 1) @Max(value = 100) String translation,
        Optional<String> transcription,
        @NotNull SupportedLanguages userBaseLanguage) {

    public CreateFlashCardDto {
        if (transcription.isPresent()) {
           transcription = transcription.filter(s -> !s.isBlank() || !s.isEmpty());
        }
    }
}
