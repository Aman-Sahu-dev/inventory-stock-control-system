package com.example.ics.Controller;

import com.example.ics.Dto.StockMovementRequest;
import com.example.ics.Dto.StockMovementsResponse;
import com.example.ics.Service.StockMovementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockmovement")
@RequiredArgsConstructor
public class StockMovementsController {
    private final StockMovementsService stockMovementsService;
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PostMapping("/receive")
    public ResponseEntity<StockMovementsResponse> receiveStock(@RequestBody StockMovementRequest request){
        return ResponseEntity.status(201).body(stockMovementsService.receiveStock(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PostMapping("/dispatch")
    public ResponseEntity<StockMovementsResponse> dispatchStock(@RequestBody StockMovementRequest request){
        return ResponseEntity.status(201).body(stockMovementsService.dispatchStock(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
    @PatchMapping
    public ResponseEntity<StockMovementsResponse> adjustStock(@RequestBody StockMovementRequest request){
        return ResponseEntity.ok(stockMovementsService.adjustStock(request));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<List<StockMovementsResponse>> getMovementByProduct(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementsService.getMovementsByProduct(id));
    }
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<StockMovementsResponse>> getMovementByWarehouse(@PathVariable Long id){
        return ResponseEntity.ok(stockMovementsService.getAllMovementsByWarehouse(id));
    }
}
