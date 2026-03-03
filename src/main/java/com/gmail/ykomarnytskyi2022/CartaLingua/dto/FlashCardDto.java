package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record FlashCardDto(
        @NotNull UUID id,
        @NotNull @Valid WordDto wordDto,
        @NotNull @NotBlank @Size(min = 1, max = 100) String translation,
        Optional<String> transcription,
        @NotNull LocalDate creationDate,
        @NotNull SupportedLanguages userBaseLanguage
) {
    public FlashCardDto {
        if (transcription == null) {
            transcription = Optional.empty();
        }
    }
}
