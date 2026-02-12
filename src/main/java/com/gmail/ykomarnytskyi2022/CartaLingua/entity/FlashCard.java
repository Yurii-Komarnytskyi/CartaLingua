package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.persistence.*;
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
    @Column(nullable = false)
    private  String translation;

    private String transcription;

    @NotNull
    @Column(nullable = false)
    private SupportedLanguages languageLearned;

    @CreationTimestamp
    private LocalDate creationDate;

    public FlashCard() {
    }

    public FlashCard(UUID id, Word word, String translation, String transcription, SupportedLanguages languageLearned) {
        this.id = id;
        this.word = word;
        this.translation = translation.strip();
        this.transcription = transcription;
        this.languageLearned = languageLearned;
    }

    public UUID getId() {
        return id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public SupportedLanguages getLanguageLearned() {
        return languageLearned;
    }

    public void setLanguageLearned(SupportedLanguages languageLearned) {
        this.languageLearned = languageLearned;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlashCard flashCard)) return false;
        return Objects.equals(id, flashCard.id) && Objects.equals(word, flashCard.word)
                && Objects.equals(translation, flashCard.translation)
                && Objects.equals(transcription, flashCard.transcription)
                && languageLearned == flashCard.languageLearned
                && Objects.equals(creationDate, flashCard.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, translation, transcription, languageLearned, creationDate);
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "id=" + id +
                ", word=" + word +
                ", translation='" + translation + '\'' +
                ", transcription='" + transcription + '\'' +
                ", languageLearned=" + languageLearned +
                ", creationDate=" + creationDate +
                '}';
    }
}
