package com.gmail.ykomarnytskyi2022.CartaLingua.service.api;

import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.dto.FlashCardDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FlashCardService {
    FlashCardDto create(CreateFlashCardDto dto);
    Optional<FlashCardDto> findById(String id);
    Page<FlashCardDto> findAll(String id);
    FlashCardDto update(FlashCardDto dto);
    void deleteById(String id);
}
