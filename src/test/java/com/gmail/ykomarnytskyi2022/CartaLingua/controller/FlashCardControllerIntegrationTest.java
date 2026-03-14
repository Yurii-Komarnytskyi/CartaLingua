package com.gmail.ykomarnytskyi2022.CartaLingua.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class FlashCardControllerIntegrationTest {

    private FlashCardController controller;
    private MockMvc mockMvc;
    private final String FLASHCARD_API  = "http://localhost:8080/api/flashcard/";
    private final String APPLICATION_JSON = "application/json";

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
    public FlashCardControllerIntegrationTest(FlashCardController controller, MockMvc mockMvc) {
        this.controller = controller;
        this.mockMvc = mockMvc;
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
    void create() {

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