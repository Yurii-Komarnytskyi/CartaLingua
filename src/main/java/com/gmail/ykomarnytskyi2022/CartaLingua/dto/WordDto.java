package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;

import java.util.UUID;

public record WordDto(UUID id, String value, SupportedLanguages language) {
}
