package com.gmail.ykomarnytskyi2022.CartaLingua.mapper;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FlashCardMapper {

    private final WordMapper wordMapper;

    public FlashCardMapper(WordMapper wordMapper) {
        this.wordMapper = wordMapper;
    }

    public FlashCard toFlashCard(@Valid CreateFlashCardDto dto) {
        return new FlashCard(
                wordMapper.toWord(dto.createWordDto()),
                dto.translation(),
                dto.transcription().orElse(""),
                dto.userBaseLanguage()
        );
    }

    public FlashCardDto toFlashCardDto(@Valid FlashCard flashCard) {
        Optional<String> transcription = Optional.ofNullable(flashCard.getTranscription()).filter(s -> !s.isEmpty() && !s.isBlank());
        return new FlashCardDto(
                flashCard.getId(),
                wordMapper.toWordDto(flashCard.getWord()),
                flashCard.getTranslation(),
                transcription,
                flashCard.getCreationDate(),
                flashCard.getUserBaseLanguage()
        );
    }

    public FlashCard toFlashCard(@Valid FlashCardDto dto) {
        return new FlashCard(
                dto.id(),
                wordMapper.toWord(dto.wordDto()),
                dto.translation(),
                dto.transcription().orElse(""),
                dto.userBaseLanguage());
    }
}
