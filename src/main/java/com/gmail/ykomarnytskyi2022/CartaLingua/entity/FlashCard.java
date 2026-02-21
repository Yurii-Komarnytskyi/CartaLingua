package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "flashcards")
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "word_id" , nullable = false)
    private Word word;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(nullable = false)
    private  String translation;

    private String transcription;

    @NotNull
    @Column(nullable = false)
    private SupportedLanguages userBaseLanguage;

    @CreationTimestamp
    private LocalDate creationDate;

    public FlashCard() {
        /*The entity class must have a public or protected no-argument constructor
        https://docs.hibernate.org/orm/5.1/userguide/html_single/chapters/domain/entity.html*/
    }

    public FlashCard(UUID id, Word word, String translation, String transcription, SupportedLanguages userBaseLanguage) {
        this.id = id;
        this.word = word;
        this.translation = translation.strip();
        this.transcription = transcription;
        this.userBaseLanguage = userBaseLanguage;
    }

    public FlashCard(Word word, String translation, String transcription, SupportedLanguages userBaseLanguage) {
        this.word = word;
        this.translation = translation;
        this.transcription = transcription;
        this.userBaseLanguage = userBaseLanguage;
    }

    public UUID getId() {
        return id;
    }

    public Word getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getTranscription() {
        return transcription;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public SupportedLanguages getUserBaseLanguage() {
        return userBaseLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlashCard flashCard)) return false;
        return Objects.equals(id, flashCard.id) && Objects.equals(word, flashCard.word)
                && Objects.equals(translation, flashCard.translation)
                && Objects.equals(transcription, flashCard.transcription)
                && userBaseLanguage == flashCard.userBaseLanguage
                && Objects.equals(creationDate, flashCard.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, translation, transcription, userBaseLanguage, creationDate);
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "id=" + id +
                ", word=" + word +
                ", translation='" + translation + '\'' +
                ", transcription='" + transcription + '\'' +
                ", languageLearned=" + userBaseLanguage +
                ", creationDate=" + creationDate +
                '}';
    }
}
