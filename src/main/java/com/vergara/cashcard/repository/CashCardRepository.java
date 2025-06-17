package com.vergara.cashcard.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.vergara.cashcard.model.CashCard;

public interface CashCardRepository extends CrudRepository<CashCard, Long> {
    @Query("SELECT * FROM cash_card WHERE owner = :owner")
    Iterable<CashCard> findByOwner(String owner);
}