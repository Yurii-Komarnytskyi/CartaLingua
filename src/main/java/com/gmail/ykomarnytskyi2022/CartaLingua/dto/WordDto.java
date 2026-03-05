package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizable;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizer;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record WordDto(@NotNull UUID id,
                      @NotNull @NotBlank @Size(min = 1, max = 100) String value,
                      @NotNull SupportedLanguages language) implements TextNormalizable<WordDto> {

    @Override
    public WordDto normalizedTextInstance() {
        return new WordDto(this.id(), TextNormalizer.normalize(this.value()), this.language());
    }
}
