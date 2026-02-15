package com.gmail.ykomarnytskyi2022.CartaLingua.service.api;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlashCardService {
    FlashCardDto create(CreateFlashCardDto dto);
    Optional<FlashCardDto> findById(UUID id);
    Page<FlashCardDto> findAllByIds(List<UUID> uuids);
    FlashCardDto update(FlashCardDto dto);
    void deleteById(UUID id);
}
