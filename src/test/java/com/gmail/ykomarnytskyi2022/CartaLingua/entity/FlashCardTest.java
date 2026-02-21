package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {

    private static Validator validator;

    private final String MUSTNT_BE_NULL = "must not be null";

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("no-arg constructor leaves all fields null")
    void noArgConstructorLeavesFieldsNull() {
        FlashCard card = new FlashCard();
        assertThat(card.getId()).isNull();
        assertThat(card.getWord()).isNull();
        assertThat(card.getTranslation()).isNull();
        assertThat(card.getUserBaseLanguage()).isNull();
    }

    @Test
    @DisplayName("canonical FlashCard constructor, happy path")
    void canonicalConstructor() {
        UUID id = UUID.randomUUID();
        Word word = new Word();
        String translation = "hello";
        String transcription = "/həˈloʊ/";
        SupportedLanguages baseLang = SupportedLanguages.ENGLISH;

        FlashCard flashCard = new FlashCard(id, word, translation, transcription, baseLang);

        assertThat(flashCard.getId()).isEqualTo(id);
        assertThat(flashCard.getWord()).isSameAs(word);
        assertThat(flashCard.getTranslation()).isEqualTo(translation);
        assertThat(flashCard.getTranscription()).isEqualTo(transcription);
        assertThat(flashCard.getUserBaseLanguage()).isEqualTo(baseLang);
        assertThat(flashCard.getCreationDate()).isNull();
    }

    @Test
    @DisplayName("no id constructor happy path")
    void noIdConstructor() {
        Word word = new Word();
        String translation = "guten Tag";
        String transcription = "/gooten tag/";
        SupportedLanguages baseLang = SupportedLanguages.GERMAN;

        FlashCard card = new FlashCard(word, translation, transcription, baseLang);

        assertThat(card.getId()).isNull();
        assertThat(card.getWord()).isSameAs(word);
        assertThat(card.getTranslation()).isEqualTo(translation);
        assertThat(card.getTranscription()).isEqualTo(transcription);
        assertThat(card.getUserBaseLanguage()).isEqualTo(baseLang);
        assertThat(card.getCreationDate()).isNull();
    }

    @Test
    @DisplayName("translation field is stripped of spaces")
    void translationStripping() {
        Word word = new Word();
        String translationWithSpaces = "   world   ";
        String transcription = null;
        SupportedLanguages lang = SupportedLanguages.ENGLISH;

        FlashCard card = new FlashCard(UUID.randomUUID(), word, translationWithSpaces, transcription, lang);

        assertThat(card.getTranslation()).isEqualTo("world");
    }

    @Test
    @DisplayName("Word field nullability")
    void wordFieldIsNull() {
        FlashCard card = new FlashCard(null, "hello", null, SupportedLanguages.ENGLISH);

        Set<ConstraintViolation<FlashCard>> violations = validator.validate(card);

        assertEquals(1, violations.size());
        ConstraintViolation<FlashCard> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("word");
        assertThat(violation.getMessage()).contains(MUSTNT_BE_NULL);
    }

    @Test
    @DisplayName("translation field nullability")
    void translationFieldIsNull() {
        FlashCard card = new FlashCard(new Word(), null, null, SupportedLanguages.ENGLISH);

        Set<ConstraintViolation<FlashCard>> violations = validator.validate(card);

        ConstraintViolation<FlashCard> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("translation");
        assertThat(violation.getMessage()).contains(MUSTNT_BE_NULL);
    }

    @Test
    @DisplayName("translation field empty or blank")
    void translationFieldEmpty() {
        FlashCard card = new FlashCard(new Word(), "", null, SupportedLanguages.ENGLISH);

        Set<ConstraintViolation<FlashCard>> violations = validator.validate(card);
        assertEquals(2, violations.size());

        for (var violation : violations) {
            assertThat(violation.getMessage()).containsAnyOf("must not be blank", "must not be empty");
        }
    }

    @Test
    @DisplayName("userBaseLanguage field nullability")
    void shouldFailValidation_whenUserBaseLanguageIsNull() {
        FlashCard card = new FlashCard(new Word(), "hello", null, null);

        Set<ConstraintViolation<FlashCard>> violations = validator.validate(card);

        assertEquals(1, violations.size());
        ConstraintViolation<FlashCard> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("userBaseLanguage");
        assertThat(violation.getMessage()).contains(MUSTNT_BE_NULL);
    }
}