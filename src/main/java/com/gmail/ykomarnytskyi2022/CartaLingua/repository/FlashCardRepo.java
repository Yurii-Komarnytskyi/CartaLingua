package com.gmail.ykomarnytskyi2022.CartaLingua.repository;

import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlashCardRepo extends JpaRepository<FlashCard, UUID> {
}
