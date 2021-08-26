package com.ppapierz.cashmachine.Controller;

import com.ppapierz.cashmachine.Model.Card;
import com.ppapierz.cashmachine.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        return modelAndView;
    }

    @GetMapping("/balance")
    public ResponseEntity<Card> balance() {
        long number = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return cardService.findByNumber(number)
                .map(card -> ResponseEntity.ok(card))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/changepin")
    public ModelAndView enterOldPin() {
        ModelAndView modelAndView = new ModelAndView("changepin");
        modelAndView.addObject("card", new Card());
        return modelAndView;
    }

    @PostMapping(value = "/changepin", params = "oldPin")
    public ModelAndView enterNewPin(@ModelAttribute("card") Card card) {
        ModelAndView modelAndView = new ModelAndView("changepin");
        modelAndView.addObject("card", card);
        cardService.checkOldPin(card);
        return modelAndView;
    }

    @PostMapping(value = "/changepin")
    public void changePin(HttpServletResponse response, @ModelAttribute("card") Card card) throws IOException {
        cardService.changePin(card);
        response.sendRedirect("/dashboard");
    }
}
