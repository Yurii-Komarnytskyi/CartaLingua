package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.WordMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.WordRepo;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.WordService;
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
public class WordServiceImpl implements WordService {

    private final WordRepo wordRepo;
    private final WordMapper mapper;

    public WordServiceImpl(WordRepo wordRepo, WordMapper mapper) {
        this.wordRepo = wordRepo;
        this.mapper = mapper;
    }

    @Override
    public WordDto create(CreateWordDto dto) {
        CreateWordDto dtoNormalized = dto.normalizedTextInstance();
        Word existingWord = wordRepo.findByValueAndLanguage(dtoNormalized.value(), dtoNormalized.language());
        if (existingWord != null) {
            return mapper.toWordDto(existingWord);
        } else {
            Word saved = wordRepo.save(mapper.toWord(dtoNormalized));
            return mapper.toWordDto(saved);
        }
    }

    @Override
    public Optional<WordDto> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        } else {
            return wordRepo.findById(id).map(mapper::toWordDto);
        }
    }

    @Override
    public Page<WordDto> findAllByIds(List<UUID> uuids) {
        if (uuids == null || uuids.size() <= 1) {
            throw new IllegalArgumentException("Argument List<UUID> uuids cannot be null or have size less than 2");
        }
        return wordRepo.findAllByIdIn(uuids, PageRequest.of(0, uuids.size()))
                .map(mapper::toWordDto);
    }

    @Override
    public WordDto update(WordDto dto) {
        Word saved = wordRepo.save(mapper.toWord(dto.normalizedTextInstance()));
        return mapper.toWordDto(saved);
    }

    @Override
    public void deleteById(UUID id) {
        if (id != null) {
            wordRepo.deleteById(id);
        }
    }

    @Override
    public boolean checkIfWordExists(WordDto dto) {
        return wordRepo.existsById(dto.id());
    }
}
