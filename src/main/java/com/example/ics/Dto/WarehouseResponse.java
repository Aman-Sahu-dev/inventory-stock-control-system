package com.example.ics.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class WarehouseResponse {
    private Long id;
    private String name;
    private String location;
}
