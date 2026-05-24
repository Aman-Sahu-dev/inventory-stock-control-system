package com.example.ics.Repository;
import com.example.ics.Models.StockMovements;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StockMovementsRepository extends JpaRepository<Long, StockMovements> {
}
