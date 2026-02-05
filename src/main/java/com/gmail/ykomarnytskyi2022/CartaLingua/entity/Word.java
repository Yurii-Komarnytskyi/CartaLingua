package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "words")
public class Word {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty
    @NotBlank
    @Column(nullable = false)
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupportedLanguages language;

    public Word() {
    }

    public Word(UUID id, String value, SupportedLanguages language) {
        this.id = id;
        this.value = value.strip();
        this.language = language;
    }

    public UUID getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public SupportedLanguages getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word word)) return false;
        return Objects.equals(id, word.id) && Objects.equals(value, word.value) && language == word.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, language);
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", language=" + language +
                '}';
    }
}
