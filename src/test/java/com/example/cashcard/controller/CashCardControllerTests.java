package com.example.cashcard.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.vergara.cashcard.controller.CashCardController;
import com.vergara.cashcard.model.CashCard;
import com.vergara.cashcard.service.CashCardService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CashCardController.class)
public class CashCardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CashCardService service;

    @Test
    @WithMockUser(username = "ivyvergara")
    void shouldReturnCashCardsForAuthenticatedUser() throws Exception {
        CashCard card = new CashCard(1L, 100.0, "ivyvergara");
        when(service.findAllByOwner("ivyvergara")).thenReturn(List.of(card));

        mockMvc.perform(get("/api/cashcards"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].balance").value(100.0));
    }

    @Configuration
    static class TestConfig {
        @Bean
        public CashCardService cashCardService() {
            return Mockito.mock(CashCardService.class);
        }
    }
}