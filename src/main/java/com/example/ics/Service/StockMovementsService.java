package com.example.ics.Service;

import com.example.ics.Dto.StockMovementRequest;
import com.example.ics.Dto.StockMovementsResponse;
import com.example.ics.Models.*;
import com.example.ics.Repository.ProductRepository;
import com.example.ics.Repository.ProductWarehouseRepository;
import com.example.ics.Repository.StockMovementsRepository;
import com.example.ics.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockMovementsService {
    private final ProductWarehouseRepository productWarehouseRepository;
    private final StockMovementsRepository stockMovementsRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    @Transactional
    private StockMovementsResponse toResponse(StockMovements movements){
        return StockMovementsResponse.builder()
                .stockMovementId(movements.getId())
                .productId(movements.getProduct().getId())
                .productName(movements.getProduct().getName())
                .warehouseId(movements.getWarehouse().getId())
                .warehouseName(movements.getWarehouse().getName())
                .movementsType(movements.getMovementType())
                .reason(movements.getReason())
                .createdAt(movements.getCreatedAt())
                .build();
    }
    @Transactional
    private StockMovementsResponse receiveStock(StockMovementRequest request){
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new RuntimeException("product doesnt found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()-> new RuntimeException("warehouse doesnt found"));
        ProductWarehouseId pwId = new ProductWarehouseId(product.getId(),warehouse.getId());
        ProductWarehouse pw = productWarehouseRepository.findById(pwId).orElse(ProductWarehouse.builder()
                .id(pwId)
                .product(product)
                .warehouse(warehouse)
                .currentStock(0)
                .build());
        pw.setCurrentStock(pw.getCurrentStock()+request.getQuantity());
        productWarehouseRepository.save(pw);

        StockMovements movements = StockMovements.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(request.getQuantity())
                .movementType("in")
                .reason(request.getReason())
                .build();
        return toResponse(movements);
    }
    @Transactional
    public StockMovementsResponse dispatchStock(StockMovementRequest request){
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new RuntimeException("product doesnt found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()-> new RuntimeException("warehouse not found"));
        ProductWarehouseId pwId = new ProductWarehouseId(product.getId(),warehouse.getId());
        ProductWarehouse pw = productWarehouseRepository.findById(pwId).orElseThrow(()-> new RuntimeException("no stock found for this product in warehouse"));
        if(pw.getCurrentStock() < request.getQuantity()){
            throw new RuntimeException("insufficient stock");
        }
        pw.setCurrentStock(pw.getCurrentStock()-request.getQuantity());
        productWarehouseRepository.save(pw);
        StockMovements movements = StockMovements.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(request.getQuantity())
                .movementType("OUT")
                .reason(request.getReason())
                .build();
        return toResponse(movements);
    }
}
