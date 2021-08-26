package com.ppapierz.cashmachine.Controller;

import com.ppapierz.cashmachine.Service.BanknoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BanknoteController {

    private final BanknoteService banknoteService;

    public BanknoteController(BanknoteService banknoteService) {
        this.banknoteService = banknoteService;
    }

    @GetMapping("/withdraw")
    public ModelAndView selectAmount() {
        ModelAndView modelAndView = new ModelAndView("withdraw");
        return modelAndView;
    }

    @GetMapping("/withdraw/{amount}")
    public String withdraw(@PathVariable("amount") int amount) {
        return banknoteService.withdraw(amount);
    }

}
