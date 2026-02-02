package com.gmail.ykomarnytskyi2022.CartaLingua.repository;

import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlashCardRepo extends JpaRepository<FlashCard, UUID> {
}
