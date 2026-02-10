package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record WordDto(@NotNull UUID id,
                      @NotNull @NotBlank @Min(value = 1) @Max(value = 100)String value,
                      @NotNull SupportedLanguages language) {
}
