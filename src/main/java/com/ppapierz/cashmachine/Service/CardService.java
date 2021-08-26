package com.ppapierz.cashmachine.Service;

import com.ppapierz.cashmachine.Model.Card;
import com.ppapierz.cashmachine.Repository.CardRepository;
import com.ppapierz.cashmachine.exception.WrongPinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Optional<Card> findByNumber(long number) {
        return cardRepository.findByNumber(number);
    }
    public void save(Card card) {
        Card test = cardRepository.save(card);
    }

    public ResponseEntity checkOldPin(Card card) {
        String currentPin = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        if (currentPin.equals(card.getPin())) {
            return new ResponseEntity(card, HttpStatus.OK);
        }
        else {
            throw new WrongPinException();
        }
    }

    @Transactional
    public Optional<Card> changePin(Card card) {
        long cardNumber = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Card> currentCard = findByNumber(cardNumber);
        currentCard.get().setPin(card.getPin());
        return currentCard;
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }
}
