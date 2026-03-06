package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.FlashCardMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.FlashCardRepo;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.FlashCardService;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.WordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class FlashCardServiceImpl implements FlashCardService {

    private final WordService wordService;
    private final FlashCardRepo repo;
    private final FlashCardMapper mapper;

    public FlashCardServiceImpl(WordService wordService, FlashCardRepo repo, FlashCardMapper mapper) {
        this.wordService = wordService;
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public FlashCardDto create(CreateFlashCardDto dto) {
        CreateFlashCardDto normalized = dto.normalizedTextInstance();
        List<FlashCard> flashCards = repo.findAllByTranslationAndUserBaseLanguage(normalized.translation(), normalized.userBaseLanguage());
        CreateWordDto createWordDto = normalized.createWordDto().normalizedTextInstance();
        for (var flashCard : flashCards) {
            Word word = flashCard.getWord();
            if (word.getValue().equals(createWordDto.value()) && word.getLanguage().equals(createWordDto.language())) {
                return mapper.toFlashCardDto(flashCard);
            }
        }
        FlashCard saved = repo.save(mapper.toFlashCardWithWordDto(normalized, wordService.create(createWordDto)));
        return mapper.toFlashCardDto(saved);
    }

    @Override
    public Optional<FlashCardDto> findById(UUID id) {
        return repo.findById(id)
                .map(mapper::toFlashCardDto);
    }

    @Override
    public Page<FlashCardDto> findAllByIds(List<UUID> uuids) {
        return repo.findAllByIdIn(uuids, PageRequest.of(0, uuids.size()))
                .map((mapper::toFlashCardDto));
    }

    @Override
    public FlashCardDto update(FlashCardDto dto) {
        FlashCardDto flashCardNormalized = dto.normalizedTextInstance();
        WordDto wordDtoNormalized = flashCardNormalized.wordDto().normalizedTextInstance();
        boolean hasWordValueChanged = wordService.findById(wordDtoNormalized.id())
                .filter(wordDto -> !(wordDto.value().equals(wordDtoNormalized.value())))
                .isPresent();
        if (hasWordValueChanged) {
            flashCardNormalized = new FlashCardDto(flashCardNormalized.id(),
                    wordService.create(new CreateWordDto(wordDtoNormalized.value(), wordDtoNormalized.language())),
                    flashCardNormalized.translation(),
                    flashCardNormalized.transcription(),
                    flashCardNormalized.creationDate(),
                    flashCardNormalized.userBaseLanguage());
        }

        FlashCard saved = repo.save(mapper.toFlashCard(flashCardNormalized));
        return mapper.toFlashCardDto(saved);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }
}
