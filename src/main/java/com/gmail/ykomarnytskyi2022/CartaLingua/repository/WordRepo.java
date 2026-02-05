package com.gmail.ykomarnytskyi2022.CartaLingua.repository;

import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WordRepo extends JpaRepository<Word, UUID> {
    Page<Word> findAllByIdIn(List<UUID> id, Pageable pageable);
}
