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

import java.util.List;
import java.util.stream.Collectors;

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
    public StockMovementsResponse receiveStock(StockMovementRequest request){
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
        StockMovements saved = stockMovementsRepository.save(movements);
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
        StockMovements saved = stockMovementsRepository.save(movements);
        return toResponse(movements);
    }
    @Transactional
    public StockMovementsResponse adjustStock(StockMovementRequest request){
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new RuntimeException("product not found"));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId()).orElseThrow(()-> new RuntimeException("warehouse not found"));
        ProductWarehouseId pwId = new ProductWarehouseId(product.getId(),warehouse.getId());
        ProductWarehouse pw = productWarehouseRepository.findById(pwId).orElse(ProductWarehouse.builder()
                .id(pwId)
                .product(product)
                .warehouse(warehouse)
                .currentStock(0)
                .build());
        int newStock = pw.getCurrentStock()+request.getQuantity();
        if(newStock < 0){
            throw new RuntimeException("adjustment will result in negative");
        }
        pw.setCurrentStock(newStock);
        productWarehouseRepository.save(pw);
        StockMovements movements = StockMovements.builder()
                .product(product)
                .warehouse(warehouse)
                .quantity(request.getQuantity())
                .movementType("ADJUSTMENT")
                .reason(request.getReason())
                .build();
        StockMovements saved = stockMovementsRepository.save(movements);
        return toResponse(saved);

        }
        @Transactional(readOnly = true)
        public List<StockMovementsResponse> getMovementsByProduct(Long productId){
            Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("product not found"));
            return stockMovementsRepository.findByProductOrderByCreatedAtDesc(product)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }
        @Transactional(readOnly = true)
        public List<StockMovementsResponse> getAllMovementsByWarehouse(Long id){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()-> new RuntimeException("warehouse not found"));
        return
                stockMovementsRepository.findByWarehouseOrderByCreatedAtDesc(warehouse)
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());
        }
}
