package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizable;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.UUID;

public record UpdateFlashCardDto(
        @NotNull UUID id,
        @NotNull @Valid WordDto wordDto,
        @NotNull @NotBlank @Size(min = 1, max = 100) String translation,
        Optional<String> transcription,
        @NotNull SupportedLanguages userBaseLanguage) implements TextNormalizable<UpdateFlashCardDto> {
    public UpdateFlashCardDto {
        if (transcription == null) {
            transcription = Optional.empty();
        }
    }

    @Override
    public UpdateFlashCardDto normalizedTextInstance() {
        return new UpdateFlashCardDto(
                this.id,
                this.wordDto,
                TextNormalizer.normalize(this.translation),
                this.transcription,
                this.userBaseLanguage
        );
    }
}
