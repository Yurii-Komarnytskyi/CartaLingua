package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.constraints.*;

public record CreateWordDto (@NotNull @NotBlank @Size(min = 1, max = 100) String value,
                             @NotNull SupportedLanguages language){
}
