package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String value;

    @Enumerated(EnumType.STRING)
    private SupportedLanguages language;

    public Word() {
    }

    public UUID getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SupportedLanguages getLanguage() {
        return language;
    }

    public void setLanguage(SupportedLanguages language) {
        this.language = language;
    }
}
