package com.ppapierz.cashmachine.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private long number;
    private String pin;
    private double balance;

    public Card() {

    }

    public Card(long number, String pin, double balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }
}
