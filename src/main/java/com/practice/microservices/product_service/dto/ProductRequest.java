package com.practice.microservices.product_service.dto;

public record ProductRequest(String id,String name, String description,java.math.BigDecimal price) {

}
