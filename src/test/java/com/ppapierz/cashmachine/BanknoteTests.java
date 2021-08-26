package com.ppapierz.cashmachine;

import com.ppapierz.cashmachine.Model.Banknote;
import com.ppapierz.cashmachine.Model.Card;
import com.ppapierz.cashmachine.Repository.BanknoteRepository;
import com.ppapierz.cashmachine.Service.BanknoteService;
import com.ppapierz.cashmachine.Service.CardService;
import com.ppapierz.cashmachine.exception.CardMoneyLimitExceedsException;
import com.ppapierz.cashmachine.exception.CashBanknoteLimitException;
import com.ppapierz.cashmachine.exception.CashLimitExceedsException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BanknoteTests {

    private final List<Banknote> banknotes = Lists.list(
            new Banknote(10, 1),
            new Banknote(20, 1),
            new Banknote(50, 2),
            new Banknote(100, 2),
            new Banknote(200, 2),
            new Banknote(500, 2)
    );

    @Test
    void withdraw_amount_is_bigger_than_available_cash_throwCashLimitExceedsException() {
        BanknoteService banknoteServiceTest = mockConfigurationReturnBanknoteService();

        assertThatExceptionOfType(CashLimitExceedsException.class)
                .isThrownBy(() -> banknoteServiceTest.withdraw(10000));
    }

    @Test
    void withdraw_amount_is_bigger_than_available_card_money_throwCardMoneyLimitExceedsException() {
        BanknoteService banknoteServiceTest = mockConfigurationReturnBanknoteService();

        assertThatExceptionOfType(CardMoneyLimitExceedsException.class)
                .isThrownBy(() -> banknoteServiceTest.withdraw(100));
    }

    @Test
    void withdraw_amount_of_available_banknotes_are_too_small_throwCashBanknoteLimitException() {
        BanknoteService banknoteServiceTest = mockConfigurationReturnBanknoteService();

        assertThatExceptionOfType(CashBanknoteLimitException.class)
                .isThrownBy(() -> banknoteServiceTest.withdraw(40));
    }

    @Test
    void withdraw_should_reduce_amount() {
        BanknoteService banknoteServiceTest = mockConfigurationReturnBanknoteService();

        banknoteServiceTest.withdraw(10);
        assertThat(banknotes.get(0).getAmount()).isEqualTo(0);
    }

    @Test
    void withdraw_should_reduce_card_balance() {
        var mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn("1234");
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
        var mockCardService = mock(CardService.class);
        when(mockCardService.findByNumber(anyLong())).thenReturn(Optional.of(new Card(1234L, "1234", 50.0)));
        var mockBanknoteRepository = mock(BanknoteRepository.class);
        when(mockBanknoteRepository.findAll()).thenReturn(banknotes);
        var banknoteServiceTest = new BanknoteService(mockBanknoteRepository, mockCardService);

        banknoteServiceTest.withdraw(10);
        assertThat(mockCardService.findByNumber(1).get().getBalance()).isEqualTo(40.0);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private BanknoteService mockConfigurationReturnBanknoteService() {
        var mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn("1234");
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
        var mockCardService = mock(CardService.class);
        when(mockCardService.findByNumber(anyLong())).thenReturn(Optional.of(new Card(1234L, "1234", 50.0)));
        var mockBanknoteRepository = mock(BanknoteRepository.class);
        when(mockBanknoteRepository.findAll()).thenReturn(banknotes);
        return new BanknoteService(mockBanknoteRepository, mockCardService);
    }
}
