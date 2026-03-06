package com.gmail.ykomarnytskyi2022.CartaLingua.repository;

import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlashCardRepo extends JpaRepository<FlashCard, UUID> {
    Page<FlashCard> findAllByIdIn(List<UUID> id, Pageable pageable);
    List<FlashCard> findAllByTranslationAndUserBaseLanguage(String translation, SupportedLanguages userBaseLanguage);
}
