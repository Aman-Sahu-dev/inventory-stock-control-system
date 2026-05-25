package com.example.ics.Repository;
import com.example.ics.Models.ProductWarehouse;
import com.example.ics.Models.ProductWarehouseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductWarehouseRepository extends JpaRepository<ProductWarehouse, ProductWarehouseId> {
}
