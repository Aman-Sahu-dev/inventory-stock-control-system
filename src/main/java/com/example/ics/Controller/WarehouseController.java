package com.example.ics.Controller;
import com.example.ics.Dto.WarehouseRequest;
import com.example.ics.Dto.WarehouseResponse;
import com.example.ics.Service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<WarehouseResponse> addWarehouse(@RequestBody WarehouseRequest request){
        return ResponseEntity.status(201).body(warehouseService.addWarehouse(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(@PathVariable Long id,@RequestBody WarehouseRequest request){
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id){
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(warehouseService.getById(id));
    }
    @GetMapping
    public ResponseEntity<Page<WarehouseResponse>> getAll(Pageable pageable){
        return ResponseEntity.ok(warehouseService.getAll(pageable));
    }
}
