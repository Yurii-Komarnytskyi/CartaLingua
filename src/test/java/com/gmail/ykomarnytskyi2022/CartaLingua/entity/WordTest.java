package com.gmail.ykomarnytskyi2022.CartaLingua.entity;

import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    private static Word word;
    private static Word sameWord;
    private static Word sameDutchWord;

    @BeforeAll
    static void BeforeAll() {
        UUID id = UUID.randomUUID();
        String value = "bank";
        word = new Word(id, value , SupportedLanguages.ENGLISH);
        sameWord = new Word(id, value, SupportedLanguages.ENGLISH);
        sameDutchWord = new Word(id, value, SupportedLanguages.DUTCH);
    }

    @AfterAll
    static void afterAll() {
        word = null;
        sameWord = null;
        sameDutchWord = null;
    }

    @Test
    void testEquals() {
        assertEquals(word, sameWord);
        assertNotEquals(word, sameDutchWord);
    }

    @Test
    void testHashCode() {
        assertEquals(word.hashCode(), sameWord.hashCode());
        assertNotEquals(word.hashCode(), sameDutchWord.hashCode());
    }

    @Test
    void testCanonicalConstructor() {
        final String blanksAtBothSides = " %s ".formatted(word.getValue());
        assertEquals(word, new Word(word.getId(), blanksAtBothSides, word.getLanguage()));
        assertNotEquals(word, new Word(word.getId(), blanksAtBothSides.concat("."), word.getLanguage()));
    }
}