package com.practice.microservices.product_service.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.practice.microservices.product_service.dto.ProductRequest;
import com.practice.microservices.product_service.dto.ProductResponse;
import com.practice.microservices.product_service.model.Product;
import com.practice.microservices.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductResponse createProduct(ProductRequest productRequest)
	{
		Product product=Product.builder()
				.name(productRequest.name())
				.description(productRequest.description())
				.price(productRequest.price())
				.build();
		productRepository.save(product);
		log.info("Product created successfully");
		return new ProductResponse(product.getId(), product.getName(),product.getDescription(), product.getPrice());
	}

	public List<ProductResponse> getAllProduct() {
		return productRepository.findAll()
				.stream()
				.map(product -> new ProductResponse(product.getId(), product.getName(),product.getDescription(), product.getPrice()))
				.collect(Collectors.toList());
	}
	
}
