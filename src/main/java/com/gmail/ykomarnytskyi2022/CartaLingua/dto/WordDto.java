package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record WordDto(@NotNull UUID id,
                      @NotNull @NotBlank @Size(min = 1, max = 100) String value,
                      @NotNull SupportedLanguages language) {
}
