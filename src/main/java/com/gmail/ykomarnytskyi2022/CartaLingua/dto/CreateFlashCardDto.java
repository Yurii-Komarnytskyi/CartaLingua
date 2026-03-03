package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.constraints.*;

import java.util.Optional;

public record CreateFlashCardDto(
        @NotNull CreateWordDto createWordDto,
        @NotNull @NotBlank @Size(min = 1, max = 100) String translation,
        Optional<String> transcription,
        @NotNull SupportedLanguages userBaseLanguage) {

    public CreateFlashCardDto {
        if (transcription.isPresent()) {
           transcription = transcription.filter(s -> !s.isBlank() || !s.isEmpty());
        }
    }
}
