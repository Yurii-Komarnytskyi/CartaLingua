package com.gmail.ykomarnytskyi2022.CartaLingua.controller;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.FlashCardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/flashcard")
public class FlashCardController {

    private final FlashCardService flashCardService;

    public FlashCardController(FlashCardService flashCardService) {
        this.flashCardService = flashCardService;
    }

    @PostMapping("/create")
    ResponseEntity<FlashCardDto> create(@NotNull @Valid @RequestBody CreateFlashCardDto dto) {
        return ResponseEntity
                .status(CREATED)
                .body(flashCardService.create(dto));
    }

    @GetMapping("/find")
    ResponseEntity<FlashCardDto> findById(@NotNull @RequestBody UUID id) {
        Optional<FlashCardDto> flashCard = flashCardService.findById(id);
        if (flashCard.isPresent()) {
            return ResponseEntity.status(OK)
                    .body(flashCard.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/findAll")
    ResponseEntity<Page<FlashCardDto>> findAllByIds(@NotNull @Size(min = 1) @RequestBody List<UUID> uuids) {
        Page<FlashCardDto> foundFlashCards = flashCardService.findAllByIds(uuids);
        if (foundFlashCards.isEmpty()) {
            return  ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .status(OK)
                    .body(foundFlashCards);
        }
    }

    @PutMapping("/update") ResponseEntity<FlashCardDto> update(@NotNull @Valid @RequestBody FlashCardDto dto) {
        return ResponseEntity
                .status(CREATED)
                .body(flashCardService.update(dto));
    }

    @DeleteMapping("/delete") ResponseEntity<?> delete(@NotNull @RequestBody UUID id) {
        flashCardService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
