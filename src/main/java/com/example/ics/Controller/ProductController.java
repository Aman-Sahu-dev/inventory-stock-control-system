package com.example.ics.Controller;

import com.example.ics.Dto.ProductResponse;
import com.example.ics.Dto.RegisterProduct;
import com.example.ics.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody RegisterProduct product){
        return ResponseEntity.status(201).body(productService.createProduct(product));
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody RegisterProduct product, @PathVariable Long id){
        return ResponseEntity.ok(productService.updateProduct(id,product));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

}
