package com.programming.techie.productservice.service;

import com.programming.techie.productservice.dto.ProductRequest;
import com.programming.techie.productservice.dto.ProductResponse;
import com.programming.techie.productservice.model.Product;
import com.programming.techie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.getName()).
                price(productRequest.getPrice()).description(productRequest.getDescription()).build();

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product>  products = productRepository.findAll();
        return products.stream().map( x -> mapToProductResponse(x)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().name(product.getName()).price(product.getPrice()).description(product.getDescription()).build();
    }
}
