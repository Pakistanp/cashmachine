package com.ppapierz.cashmachine;

import com.ppapierz.cashmachine.Model.Card;
import com.ppapierz.cashmachine.Repository.CardRepository;
import com.ppapierz.cashmachine.Service.CardService;
import com.ppapierz.cashmachine.exception.WrongPinException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CardTests {

    @Test
    void checkOldPin_should_return_OK_when_inserted_pin_is_equal() {
        CardRepository mockCardRepository = mockConfigurationReturning();
        var cardServiceTest = new CardService(mockCardRepository);
        assertThat(cardServiceTest.checkOldPin(new Card(0L, "123456789", 0.0)).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkOldPin_pin_not_equal_throwsWrongPinException() {
        CardRepository mockCardRepository = mockConfigurationReturning();
        var cardServiceTest = new CardService(mockCardRepository);

        assertThatExceptionOfType(WrongPinException.class)
                .isThrownBy(() -> cardServiceTest.checkOldPin(new Card(0L, "0", 0.0)));
    }

    @Test
    void changePin_should_change_pin() {
        var cardBefore = new Card(1234L, "1234", 0.0);
        var cardAfter = new Card(1234L, "4321", 0.0);

        var mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getName()).thenReturn("1234");
        var mockCardRepository = mock(CardRepository.class);
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
        var cardServiceTest = new CardService(mockCardRepository);
        when(cardServiceTest.findByNumber(1234L)).thenReturn(Optional.of(cardBefore));

        assertThat(cardServiceTest.changePin(cardAfter).get().getPin()).isEqualTo(mockCardRepository.findByNumber(1234L).get().getPin());
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private CardRepository mockConfigurationReturning() {
        var mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getCredentials()).thenReturn("123456789");
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
        return mock(CardRepository.class);
    }
}
