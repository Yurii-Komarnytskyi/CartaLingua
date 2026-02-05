package com.gmail.ykomarnytskyi2022.CartaLingua.service.api;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordService {
    WordDto create(CreateWordDto dto);
    Optional<WordDto> findById(UUID id);
    Page<WordDto> findAllByIds(List<UUID> uuids);
    WordDto update(WordDto dto);
    void deleteById(UUID id);
    boolean checkIfWordExists(WordDto dto);
}
