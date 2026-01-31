package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @OneToMany(mappedBy = "word")
    private UUID id;

    @NotNull
    private String value;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SupportedLanguages language;

    public Word() {
    }

    public Word(UUID id, String value, SupportedLanguages language) {
        this.id = id;
        this.value = value;
        this.language = language;
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
