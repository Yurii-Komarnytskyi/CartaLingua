package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizable;
import com.gmail.ykomarnytskyi2022.CartaLingua.util.TextNormalizer;
import jakarta.validation.constraints.*;

public record CreateWordDto(@NotNull @NotBlank @Size(min = 1, max = 100) String value,
                            @NotNull SupportedLanguages language) implements TextNormalizable<CreateWordDto> {
    @Override
    public CreateWordDto normalizedTextInstance() {
        return new CreateWordDto(TextNormalizer.normalize(this.value()), this.language());
    }
}
