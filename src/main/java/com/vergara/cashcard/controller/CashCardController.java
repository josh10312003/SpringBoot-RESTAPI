package com.vergara.cashcard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vergara.cashcard.model.CashCard;
import com.vergara.cashcard.service.CashCardService;

import java.security.Principal;

@RestController
@RequestMapping("/api/cashcards")
public class CashCardController {
    private final CashCardService service;

    
    public CashCardController(CashCardService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<CashCard> getCashCards(Principal principal) {
        return service.findAllByOwner(principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashCard> getCashCard(@PathVariable Long id, Principal principal) {
        return service.findById(id, principal.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CashCard createCashCard(@RequestBody CashCard card, Principal principal) {
        return service.createCashCard(card, principal.getName());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashCard> updateCashCard(@PathVariable Long id, @RequestBody CashCard updatedCard, Principal principal) {
        try {
            CashCard card = service.updateCashCard(id, updatedCard, principal.getName());
            return ResponseEntity.ok(card);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        try {
            service.deleteCashCard(id, principal.getName());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}