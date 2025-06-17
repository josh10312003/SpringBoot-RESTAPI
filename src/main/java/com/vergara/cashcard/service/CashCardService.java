package com.vergara.cashcard.service;

import org.springframework.stereotype.Service;

import com.vergara.cashcard.model.CashCard;
import com.vergara.cashcard.repository.CashCardRepository;

import java.util.Optional;

@Service
public class CashCardService {
    private final CashCardRepository repository;

    public CashCardService(CashCardRepository repository) {
        this.repository = repository;
    }

    public Iterable<CashCard> findAllByOwner(String owner) {
        return repository.findByOwner(owner);
    }

    public Optional<CashCard> findById(Long id, String owner) {
        Optional<CashCard> card = repository.findById(id);
        if (card.isPresent() && card.get().getOwner().equals(owner)) {
            return card;
        }
        return Optional.empty();
    }

    public CashCard createCashCard(CashCard card, String owner) {
        card.setOwner(owner);
        return repository.save(card);
    }

    public CashCard updateCashCard(Long id, CashCard updatedCard, String owner) {
        Optional<CashCard> card = findById(id, owner);
        if (card.isEmpty()) {
            throw new RuntimeException("CashCard not found or not owned by user");
        }
        CashCard existingCard = card.get();
        existingCard.setBalance(updatedCard.getBalance());
        return repository.save(existingCard);
    }
    
    public void deleteCashCard(Long id, String owner) {
        Optional<CashCard> card = findById(id, owner);
        if (card.isEmpty()) {
            throw new RuntimeException("CashCard not found or not owned by user");
        }
        repository.deleteById(id);
    }
}