package com.example.ics.Service;

import com.example.ics.Models.StockMovements;
import com.example.ics.Repository.ProductRepository;
import com.example.ics.Repository.ProductWarehouseRepository;
import com.example.ics.Repository.StockMovementsRepository;
import com.example.ics.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockMovementsService {
    private final ProductWarehouseRepository productWarehouseRepository;
    private final StockMovementsRepository stockMovementsRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    private StockMovementsResponse
}
