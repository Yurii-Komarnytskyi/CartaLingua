package com.gmail.ykomarnytskyi2022.CartaLingua.controller;

import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateFlashCardDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.dto.CreateWordDto;
import com.gmail.ykomarnytskyi2022.CartaLingua.enumeration.SupportedLanguages;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class FlashCardControllerIntegrationTest {

    private final FlashCardController controller;
    private final MockMvc mockMvc;
    private final JsonMapper jsonMapper;
    private final RequestPostProcessor httpBasic = httpBasic("user", "password");
    private final String FLASHCARD_API = "http://localhost:8080/api/flashcard/";
    private final String APPLICATION_JSON = "application/json";
    private final CreateFlashCardDto createDto = new CreateFlashCardDto(new CreateWordDto("goedkoop", SupportedLanguages.DUTCH),
            "cheap", Optional.of("/tʃiːp/"), SupportedLanguages.ENGLISH);

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    public FlashCardControllerIntegrationTest(FlashCardController controller, MockMvc mockMvc, JsonMapper jsonMapper) {
        this.controller = controller;
        this.mockMvc = mockMvc;
        this.jsonMapper = jsonMapper;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("sanity check if FlashCardController is loaded by the context")
    void sanityCheckFlashCardController() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("create happy path")
    void create() throws Exception {
        mockMvc.perform(
                post(FLASHCARD_API + "create")
                                .contentType(APPLICATION_JSON)
                                .content(jsonMapper.writeValueAsString(createDto))
                                .with(httpBasic))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.wordDto.id").exists(),
                        jsonPath("$.wordDto.value").value(createDto.createWordDto().value()),
                        jsonPath("$.wordDto.language").value(createDto.createWordDto().language().toString()),
                        jsonPath("$.translation").value(createDto.translation()),
                        jsonPath("$.transcription").value(createDto.transcription().orElse("none")),
                        jsonPath("$.creationDate").value(LocalDate.now().toString()),
                        jsonPath("$.userBaseLanguage").value(createDto.userBaseLanguage().toString())
                );
    }

    @Test
    void findById() {
    }

    @Test
    void findAllByIds() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}