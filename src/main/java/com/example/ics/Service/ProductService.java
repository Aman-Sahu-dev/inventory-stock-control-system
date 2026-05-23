package com.example.ics.Service;
import com.example.ics.Dto.ProductResponse;
import com.example.ics.Dto.RegisterProduct;
import com.example.ics.Models.Product;
import com.example.ics.Repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private ProductResponse toResponse(Product product){
       return ProductResponse.builder()
               .id(product.getId())
               .name(product.getName())
               .sku(product.getSku())
               .description(product.getDescription())
               .price(product.getPrice())
               .status(product.getStatus())
               .createdAt(product.getCreated_at())
               .build();
    }
    @Transactional
    public ProductResponse createProduct(RegisterProduct request){
        if(productRepository.existsBySku(request.getSku())){
            throw new RuntimeException("sku already exists: "+request.getSku());
        }
        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .description(request.getDescription())
                .price(request.getPrice())
                .status("ACTIVE")
                .build();
     Product saved =   productRepository.save(product);
     return toResponse(saved);
    }
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("product doesnt found"));
        return toResponse(product);
    }
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProduct(Pageable pageable){
        return productRepository.findAll(pageable).map(this::toResponse);
    }
    @Transactional
    public ProductResponse updateProduct(Long id,RegisterProduct request){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("product doesnt found"));
        if(request.getName() != null)  product.setName(request.getName());
        if(request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        Product update = productRepository.save(product);
        return toResponse(update);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("product doesnt found "+id));
        product.setStatus("INACTIVE");
        productRepository.save(product);
    }
}
