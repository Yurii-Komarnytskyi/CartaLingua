package com.gmail.ykomarnytskyi2022.CartaLingua.mapper;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {
    public WordDto toWordDto(@NotNull Word word) {
        return  new WordDto(word.getId(), word.getValue(), word.getLanguage());
    }

    public Word toWord(@NotNull CreateWordDto dto) {
        return new Word(dto.value(), dto.language());
    }

    public Word toWord(@NotNull WordDto dto) {
        return new Word(dto.id(), dto.value(), dto.language());
    }
}
