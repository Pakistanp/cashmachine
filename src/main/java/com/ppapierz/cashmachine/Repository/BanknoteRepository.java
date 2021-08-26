package com.ppapierz.cashmachine.Repository;

import com.ppapierz.cashmachine.Model.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
}
