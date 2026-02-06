package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.WordMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.WordRepo;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.WordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepo wordRepo;
    private final WordMapper mapper;

    public WordServiceImpl(WordRepo wordRepo, WordMapper mapper) {
        this.wordRepo = wordRepo;
        this.mapper = mapper;
    }

    @Override
    public WordDto create(CreateWordDto dto) {
        Word saved = wordRepo.save(mapper.toWord(dto));
        return  mapper.toWordDto(saved);
    }

    @Override
    public Optional<WordDto> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        } else {
            return wordRepo.findById(id).map(w -> mapper.toWordDto(w));
        }
    }

    @Override
    public Page<WordDto> findAllByIds(List<UUID> uuids) {
        return wordRepo.findAllByIdIn(uuids, PageRequest.of(0, uuids.size()))
                .map(word -> mapper.toWordDto(word));
    }

    @Override
    public WordDto update(WordDto dto) {
        Word saved = wordRepo.save(mapper.toWord(dto));
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
        return (dto != null && dto.id() != null)? wordRepo.existsById(dto.id()) : false;
    }
}
