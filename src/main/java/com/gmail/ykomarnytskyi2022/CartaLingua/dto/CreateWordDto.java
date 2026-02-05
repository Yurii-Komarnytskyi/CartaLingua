package com.gmail.ykomarnytskyi2022.CartaLingua.dto;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;

public record CreateWordDto (String value, SupportedLanguages language){
}
