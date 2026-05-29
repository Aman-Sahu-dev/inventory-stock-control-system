package com.example.ics.Service;

import com.example.ics.Dto.WarehouseRequest;
import com.example.ics.Dto.WarehouseResponse;
import com.example.ics.Models.Warehouse;
import com.example.ics.Repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    private WarehouseResponse toResponse(Warehouse warehouse){
        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .build();
    }
    @Transactional
    public WarehouseResponse addWarehouse(WarehouseRequest request){
        Warehouse warehouse = Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
        Warehouse saved = warehouseRepository.save(warehouse);
        return toResponse(saved);
    }
    @Transactional
    public WarehouseResponse updateWarehouse(Long id,WarehouseRequest request){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()-> new RuntimeException("couldn't find warehouse"));
        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        Warehouse saved = warehouseRepository.save(warehouse);
        return toResponse(saved);
    }
    @Transactional(readOnly = true)
    public WarehouseResponse getById(Long id){
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(()-> new RuntimeException("warehouse not found"));
        return toResponse(warehouse);
    }
    @Transactional(readOnly = true)
    public Page<WarehouseResponse> getAll(Pageable pageable){
        return warehouseRepository.findAll(pageable).map(this::toResponse);
    }
    @Transactional
    public void deleteWarehouse(Long id){
        warehouseRepository.deleteById(id);
    }
}
