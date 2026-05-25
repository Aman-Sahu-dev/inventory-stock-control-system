package com.example.ics.Repository;
import com.example.ics.Models.Product;
import com.example.ics.Models.StockMovements;
import com.example.ics.Models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementsRepository extends JpaRepository<StockMovements , Long> {
    List<StockMovements> findByProductOrderByCreatedAtDesc(Product product);
    List<StockMovements> findByWarehouseOrderByCreatedAtDesc(Warehouse warehouse);
}
