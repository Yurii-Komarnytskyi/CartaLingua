package com.gmail.ykomarnytskyi2022.CartaLingua.service;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.WordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.entity.Word;
import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import com.gmail.ykomarnytskyi2022.CartaLingua.mapper.WordMapper;
import com.gmail.ykomarnytskyi2022.CartaLingua.repository.WordRepo;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {

    @Mock
    private WordRepo repo;

    @Mock
    private WordMapper mapper;

    @Mock
    Page<Word> wordsPage;

    @InjectMocks
    private WordServiceImpl service;

    private final String CREEREN = "creëren";
    private final UUID ID = UUID.randomUUID();

    private Word word = new Word(ID, CREEREN, SupportedLanguages.DUTCH);
    private Word wordNullId = new Word(CREEREN, SupportedLanguages.DUTCH);

    private WordDto wordDto = new WordDto(ID, CREEREN, SupportedLanguages.DUTCH);
    private CreateWordDto createWordDto = new CreateWordDto(CREEREN, SupportedLanguages.DUTCH);

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("create() happy path")
    void create() {
        when(mapper.toWord(createWordDto)).thenReturn(wordNullId);
        when(repo.save(wordNullId)).thenReturn(word);
        when(mapper.toWordDto(word)).thenReturn(wordDto);

        WordDto actual = service.create(createWordDto);

        assertNotNull(actual);
        assertNotNull(actual.id());
        assertEquals(wordDto.id(), actual.id());
        assertEquals(wordDto.value(), actual.value());
        assertEquals(wordDto.language(), actual.language());

        verify(repo, times(1)).save(any(Word.class));
        verify(mapper, times(1)).toWordDto(any(Word.class));
    }

    @Test
    @DisplayName("create() Word already exists")
    void createWordExists() {
        when(repo.findByValueAndLanguage(createWordDto.value(), createWordDto.language())).thenReturn(word);
        when(mapper.toWordDto(word)).thenReturn(wordDto);

        WordDto actual = service.create(createWordDto);

        assertNotNull(actual);
        assertEquals(wordDto, actual);

        verify(repo, never()).save(any(Word.class));
        verify(mapper, times(1)).toWordDto(any(Word.class));
    }

    @Test
    @DisplayName("create() with empty value")
    void createEmptyValue() {
        CreateWordDto emptyValue = new CreateWordDto("", null);

        when(service.create(emptyValue)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> service.create(emptyValue));
        verify(repo, never()).save(any(Word.class));
    }

    @Test
    @DisplayName("create() with blank value")
    void createBlankValue() {
        CreateWordDto blankValue = new CreateWordDto(" ", null);
        when(service.create(blankValue)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> service.create(blankValue));

        verify(repo, never()).save(any(Word.class));
    }

    @Test
    @DisplayName("create() with exceeding value")
    void createExceedingValue() {
        CreateWordDto exceedingValue = new CreateWordDto("x".repeat(100), null);
        when(service.create(exceedingValue)).thenThrow(ConstraintViolationException.class);
        assertThrowsExactly(ConstraintViolationException.class, () -> service.create(exceedingValue));

        verify(repo, never()).save(any(Word.class));
    }

    @Test
    @DisplayName("findById() happy path")
    void findById() {
        when(repo.findById(ID)).thenReturn(Optional.of(word));
        when(mapper.toWordDto(word)).thenReturn(wordDto);

        Optional<WordDto> actual = service.findById(ID);
        Optional<WordDto> expected = Optional.of(wordDto);

        assertNotNull(actual);
        assertEquals(actual, expected);

        verify(repo, times(1)).findById(ID);
        verify(mapper, times(1)).toWordDto(word);
    }

    @Test
    @DisplayName("findById() with null id")
    void findByIdNullId() {
        Optional<WordDto> actual = service.findById(null);
        Optional<WordDto> expected = Optional.empty();


        assertEquals(actual, expected);
        verify(repo, never()).findById(null);
    }

    @Test
    @DisplayName("findAllByIds() happy path")
    void findAllByIds() {
        List<UUID> uuids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        );

        List<Word> mockWords = uuids.stream()
                .map(id -> new Word(id, "wordValue", SupportedLanguages.DUTCH))
                .toList();

        Page<Word> mockPage = new PageImpl<>(
                mockWords,
                PageRequest.of(0, uuids.size()),
                mockWords.size()
        );

        when(repo.findAllByIdIn(anyList(), any(Pageable.class)))
                .thenReturn(mockPage);

        when(mapper.toWordDto(any(Word.class)))
                .thenAnswer(invocation -> {
                    Word word = invocation.getArgument(0);
                    return new WordDto(word.getId(), word.getValue(), word.getLanguage());
                });

        Page<WordDto> result = service.findAllByIds(uuids);

        assertThat(result.getContent())
                .hasSize(uuids.size())
                .extracting(WordDto::id)
                .containsExactlyElementsOf(uuids);

        assertThat(result.getNumber()).isZero();

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

        verify(mapper, times(mockWords.size())).toWordDto(any(Word.class));
    }

    @Test
    @DisplayName("findAllByIds() empty list, list is null")
    void findAllByIdsEmptyListOrNull() {
        List<UUID> emptyList = new ArrayList<>();
        assertThrowsExactly(IllegalArgumentException.class,
                () -> repo.findAllByIdIn(emptyList, PageRequest.of(0, emptyList.size())));

        assertThrowsExactly(IllegalArgumentException.class, () -> service.findAllByIds(null));
    }

    @Test
    @DisplayName("update() happy path")
    void update() {
        when(mapper.toWord(wordDto)).thenReturn(word);
        when(repo.save(word)).thenReturn(word);
        when(mapper.toWordDto(word)).thenReturn(wordDto);

        WordDto actual = service.update(wordDto);
        assertThat(actual).isNotNull();
        assertEquals(actual.id(), wordDto.id());
        assertEquals(actual.value(), wordDto.value());
        assertEquals(actual.language(), wordDto.language());

        verify(mapper, times(1)).toWord(wordDto);
        verify(mapper, times(1)).toWordDto(word);
        verify(repo, times(1)).save(word);
    }

    @Test
    @DisplayName("update() dto with empty value")
    void updateMalformed() {
        WordDto wordDtoEmptyValue = new WordDto(ID, "", SupportedLanguages.DUTCH);

        when(service.update(wordDtoEmptyValue)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> service.update(wordDtoEmptyValue));
        verify(repo, never()).save(any(Word.class));
    }

    @Test
    @DisplayName("update() dto with null id")
    void updateMalformedNullId() {
        WordDto wordDtoNullId = new WordDto(null, "het", SupportedLanguages.DUTCH);

        when(service.update(wordDtoNullId)).thenThrow(ConstraintViolationException.class);

        assertThrowsExactly(ConstraintViolationException.class, () -> service.update(wordDtoNullId));
        verify(repo, never()).save(any(Word.class));
    }

    @Test
    @DisplayName("deleteById() happy path")
    void deleteById() {
        doNothing().when(repo).deleteById(ID);

        service.deleteById(ID);

        verify(repo, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("checkIfWordExists() happy path")
    void checkIfWordExists() {
        when(repo.existsById(any(UUID.class))).thenReturn(true);

        boolean actual = service.checkIfWordExists(wordDto);

        assertTrue(actual);
    }
}