package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.FlashCardMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.FlashCardRepo;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.FlashCardService;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.WordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
        return  null;
    }

    @Override
    public Optional<FlashCardDto> findById(@NotNull UUID id) {
        return null;
    }

    @Override
    public Page<FlashCardDto> findAll(@NotNull UUID id) {
        return null;
    }

    @Override
    public FlashCardDto update(@Valid FlashCardDto dto) {
        return null;
    }

    @Override
    public void deleteById(@NotNull UUID id) {
        repo.deleteById(id);
    }
}
