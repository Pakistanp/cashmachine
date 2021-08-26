package com.ppapierz.cashmachine.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Banknote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private int faceValue;
    private int amount;

    public Banknote() {

    }

    public Banknote(int faceValue, int amount) {
        this.faceValue = faceValue;
        this.amount = amount;
    }
}
