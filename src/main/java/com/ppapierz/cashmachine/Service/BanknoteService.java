package com.ppapierz.cashmachine.Service;

import com.ppapierz.cashmachine.Model.Banknote;
import com.ppapierz.cashmachine.Model.Card;
import com.ppapierz.cashmachine.Repository.BanknoteRepository;
import com.ppapierz.cashmachine.exception.CardMoneyLimitExceedsException;
import com.ppapierz.cashmachine.exception.CashBanknoteLimitException;
import com.ppapierz.cashmachine.exception.CashLimitExceedsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class BanknoteService {

    private final BanknoteRepository banknoteRepository;
    private final CardService cardService;

    public BanknoteService(BanknoteRepository banknoteRepository, CardService cardService) {
        this.banknoteRepository = banknoteRepository;
        this.cardService = cardService;
    }

    public void save(Banknote banknote) {
        Banknote test = banknoteRepository.save(banknote);
    }

    @Transactional
    public String withdraw(int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Banknote> banknotes = banknoteRepository.findAll();
        if(amount > banknotes.stream().mapToInt(banknote -> banknote.getAmount() * banknote.getFaceValue()).sum()){
            throw new CashLimitExceedsException(amount);
        }
        long number = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Optional<Card> card = cardService.findByNumber(number);
        if(amount > card.get().getBalance()) {
            throw new CardMoneyLimitExceedsException();
        }

        int value = amount;
        for(int i = banknotes.size() - 1; i >= 0 && value != 0; i--) {
            int exdpend = value / banknotes.get(i).getFaceValue();
            if(value >= banknotes.get(i).getFaceValue()) {
                int currentAmount = banknotes.get(i).getAmount();
                if(currentAmount < exdpend) {
                    exdpend = currentAmount;
                }
                banknotes.get(i).setAmount(banknotes.get(i).getAmount() - exdpend);
                stringBuilder.append("No of " + banknotes.get(i).getFaceValue() + "'s" + " : " + exdpend);
            }
            value -= banknotes.get(i).getFaceValue() * exdpend;
        }
        if(value != 0) {
            throw new CashBanknoteLimitException(value);
        }
        double roundedValue = new BigDecimal(card.get().getBalance() - amount).setScale(2, RoundingMode.FLOOR).doubleValue();
        card.get().setBalance(roundedValue);

        return stringBuilder.toString();
    }
}
