package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizable;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Optional;

public record CreateFlashCardDto(
        @NotNull @Valid CreateWordDto createWordDto,
        @NotNull @NotBlank @Size(min = 1, max = 100) String translation,
        Optional<String> transcription,
        @NotNull SupportedLanguages userBaseLanguage) implements TextNormalizable<CreateFlashCardDto> {

    public CreateFlashCardDto {
        if (transcription == null) {
           transcription = Optional.empty();
        } else {
            transcription = transcription.filter(s -> !s.isBlank() || !s.isEmpty());
        }
    }

    @Override
    public CreateFlashCardDto normalizedTextInstance() {
        return new CreateFlashCardDto(
                this.createWordDto,
                TextNormalizer.normalize(this.translation),
                this.transcription,
                this.userBaseLanguage);
    }
}
