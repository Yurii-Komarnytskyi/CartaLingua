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
import jakarta.persistence.Index;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public FlashCardDto create(@Valid CreateFlashCardDto dto) {
        Optional<FlashCard> flashCardPersisted = repo.findByTranslationAndUserBaseLanguage(dto.translation(), dto.userBaseLanguage());
        CreateWordDto createWordDto = dto.createWordDto();
        if (flashCardPersisted.isPresent()) {
            Word word = flashCardPersisted.get().getWord();
            if (word.getValue().equals(createWordDto.value())
                    && word.getLanguage().equals(createWordDto.language())) {
                return mapper.toFlashCardDto(flashCardPersisted.get());
            }
        }
        FlashCard saved = repo.save(mapper.toFlashCardWithWordDto(dto, wordService.create(createWordDto)));
        return mapper.toFlashCardDto(saved);
    }

    @Override
    public Optional<FlashCardDto> findById(@NotNull UUID id) {
        return repo.findById(id)
                .map((flashCard) -> mapper.toFlashCardDto(flashCard));
    }

    @Override
    public Page<FlashCardDto> findAllByIds(@NotNull List<UUID> uuids) {
        if (uuids == null || uuids.size() <= 1) {
            throw new IllegalArgumentException("Argument List<UUID> uuids cannot be null or have size less than 2");
        }
        return repo.findAllByIdIn(uuids, PageRequest.of(0, uuids.size()))
                .map((flashCard -> mapper.toFlashCardDto(flashCard)));
    }

    @Override
    public FlashCardDto update(@Valid FlashCardDto dto) {
        wordService.update(dto.wordDto());
        FlashCard saved = repo.save(mapper.toFlashCard(dto));
        return mapper.toFlashCardDto(saved);
    }

    @Override
    public void deleteById(@NotNull UUID id) {
        repo.deleteById(id);
    }
}
