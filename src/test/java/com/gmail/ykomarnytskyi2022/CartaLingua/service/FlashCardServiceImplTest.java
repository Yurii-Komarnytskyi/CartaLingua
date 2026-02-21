package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.FlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.FlashCard;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.FlashCardMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.FlashCardRepo;
import com.gmail.ykomarnytskyi2022.CartaLingua.service.api.WordService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages.DUTCH;
import static com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages.ENGLISH;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlashCardServiceImplTest {

    @Mock
    private WordService wordService;

    @Mock
    private FlashCardMapper mapper;

    @Mock
    private FlashCardRepo repo;

    @InjectMocks
    private FlashCardServiceImpl flashCardService;

    private final String KENNIS = "kennis";
    private final String KNOWLEDGE = "knowledge";
    private final UUID wordId = UUID.randomUUID();
    private final UUID flashCardId = UUID.randomUUID();

    private final CreateWordDto createWordDto = new CreateWordDto("kennis", DUTCH);
    private final CreateFlashCardDto createFlashCardDto = new CreateFlashCardDto(createWordDto, KNOWLEDGE, Optional.empty(), ENGLISH);
    private final WordDto wordDto = new WordDto(wordId, KENNIS, DUTCH);
    private final FlashCardDto flashCardDto = new FlashCardDto(flashCardId,wordDto, KNOWLEDGE, Optional.empty(), LocalDate.now(), ENGLISH);
    private final Word word = new Word(wordId, KENNIS, DUTCH);
    private final FlashCard flashCard = new FlashCard(flashCardId, word, KNOWLEDGE, "", ENGLISH);
    private final FlashCard flashCardNullId = new FlashCard(word, KNOWLEDGE, "", ENGLISH);

    @Test
    @DisplayName("create() happy path")
    void create() {
        when(wordService.create(createWordDto)).thenReturn(wordDto);
        when(mapper.toFlashCardWithWordDto(createFlashCardDto, wordDto)).thenReturn(flashCardNullId);
        when(repo.save(flashCardNullId)).thenReturn(flashCard);
        when(mapper.toFlashCardDto(flashCard)).thenReturn(flashCardDto);

        FlashCardDto actual = flashCardService.create(createFlashCardDto);

        assertNotNull(actual);
        assertEquals(actual, flashCardDto);

        verify(wordService, times(1)).create(createWordDto);
        verify(repo, times(1)).save(flashCardNullId);
        verify(mapper, times(1)).toFlashCardDto(flashCard);
    }

    @Test
    @DisplayName("create() FlashCard already exists")
    void createAlreadyExists() {
        when(repo.findByTranslationAndUserBaseLanguage(createFlashCardDto.translation(), createFlashCardDto.userBaseLanguage()))
                .thenReturn(Optional.of(flashCard));
        when(mapper.toFlashCardDto(flashCard)).thenReturn(flashCardDto);

        FlashCardDto actual = flashCardService.create(createFlashCardDto);

        assertNotNull(actual);
        assertEquals(actual, flashCardDto);

        verify(wordService, never()).create(createWordDto);
        verify(repo, never()).save(flashCardNullId);
        verify(mapper, times(1)).toFlashCardDto(flashCard);
    }

    @Test
    @DisplayName("create() invalid CreateFlashCardDto")
    void createInvalidDto() {
        CreateFlashCardDto invalidDto = new CreateFlashCardDto(null, null, Optional.empty(),null);

        when(flashCardService.create(invalidDto)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> flashCardService.create(invalidDto));
        verify(repo, never()).save(any(FlashCard.class));
    }


    @Test
    @DisplayName("findById() happy path")
    void findById() {
        when(repo.findById(flashCardId)).thenReturn(Optional.of(flashCard));
        when(mapper.toFlashCardDto(flashCard)).thenReturn(flashCardDto);

        Optional<FlashCardDto> actual = flashCardService.findById(flashCardId);
        assertTrue(actual.isPresent());
        assertEquals(actual, Optional.of(flashCardDto));

    }

    @Test
    @DisplayName("findById() null id")
    void findByNullId() {
        when(repo.findById(flashCardId)).thenReturn(Optional.of(flashCard));
        when(mapper.toFlashCardDto(flashCard)).thenReturn(flashCardDto);

        Optional<FlashCardDto> actual = flashCardService.findById(flashCardId);
        assertTrue(actual.isPresent());
        assertEquals(actual, Optional.of(flashCardDto));
    }

    @Test
    @DisplayName("findAllByIds() happy path")
    void findAllByIds() {
        List<UUID> uuids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        );

        List<FlashCard> mockFlashCards = List.of(
                new FlashCard(
                        uuids.get(0),
                        new Word(UUID.randomUUID(), "hond", DUTCH),
                        "dog",
                        null,
                        ENGLISH
                ),

                new FlashCard(
                        uuids.get(1),
                        new Word(UUID.randomUUID(), "huis", DUTCH),
                        "house",
                        "/haʊs/",
                        ENGLISH
                ),

                new FlashCard(
                        uuids.get(2),
                        new Word(UUID.randomUUID(), "boek", DUTCH),
                        "book",
                        null,
                        ENGLISH
                )
        );

        Page<FlashCard> mockPage = new PageImpl<>(
                mockFlashCards,
                PageRequest.of(0, uuids.size()),
                mockFlashCards.size());

        when(repo.findAllByIdIn(anyList(), any(Pageable.class))).thenReturn(mockPage);
        when(mapper.toFlashCardDto(any(FlashCard.class))).thenAnswer(invocation -> {
            FlashCard flashCard = invocation.getArgument(0);
            Word word = flashCard.getWord();
            WordDto wordDto = new WordDto(word.getId(), word.getValue(), word.getLanguage());
            return new FlashCardDto(
                    flashCard.getId(),
                    wordDto,
                    flashCard.getTranslation(),
                    Optional.ofNullable(flashCard.getTranscription()),
                    flashCard.getCreationDate(),
                    flashCard.getUserBaseLanguage());
        });

        Page<FlashCardDto> actual = flashCardService.findAllByIds(uuids);
        assertThat(actual.getContent()).hasSize(uuids.size())
                .extracting(FlashCardDto::id)
                .containsExactlyElementsOf(uuids);

        assertThat(actual.getNumber()).isZero();

        ArgumentCaptor<List<UUID>> idListCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(repo, times(1)).findAllByIdIn(
                idListCaptor.capture(),
                pageableCaptor.capture()
        );

        assertThat(idListCaptor.getValue())
                .isSameAs(uuids)
                .containsExactlyElementsOf(uuids);

        Pageable capturedPageable = pageableCaptor.getValue();
        assertThat(capturedPageable.getPageNumber()).isZero();
        assertThat(capturedPageable.getPageSize()).isEqualTo(uuids.size());

        verify(mapper, times(mockFlashCards.size())).toFlashCardDto(any(FlashCard.class));
    }

    @Test
    @DisplayName("findAllByIds() empty list")
    void findAllByIdsEmpty() {
        assertThrowsExactly(IllegalArgumentException.class, () -> flashCardService.findAllByIds(Collections.emptyList()));
        verify(repo, never()).findAllByIdIn(anyList(), any(Pageable.class));
    }

    @Test
    @DisplayName("update() happy path")
    void update() {
        when(wordService.update(wordDto)).thenReturn(wordDto);
        when(mapper.toFlashCard(flashCardDto)).thenReturn(flashCard);
        when(repo.save(flashCard)).thenReturn(flashCard);
        when(mapper.toFlashCardDto(flashCard)).thenReturn(flashCardDto);

        FlashCardDto actual = flashCardService.update(flashCardDto);

        assertNotNull(actual);
        assertEquals(flashCardDto, actual);

        verify(wordService, times(1)).update(wordDto);
        verify(repo, times(1)).save(flashCard);
    }

    @Test
    @DisplayName("update() invalid dto")
    void updateInvalid() {
        FlashCardDto invalidDto = new FlashCardDto(null, wordDto, "", Optional.empty(), LocalDate.now(), ENGLISH);

        when(flashCardService.update(invalidDto)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> flashCardService.update(invalidDto));
        verify(repo, never()).save(flashCard);
    }

    @Test
    void deleteById() {
        doNothing().when(repo).deleteById(flashCardId);

        flashCardService.deleteById(flashCardId);

        verify(repo, times(1)).deleteById(flashCardId);
    }
}