package com.practice.microservices.product_service;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//To run the test on random port
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:7.0.5" );
	
	@LocalServerPort   //Injects port of which our test application is running
	private Integer port;  
	
	@BeforeEach
	void setUp()
	{
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	
	static {
		//Runs before the application is started
		mongoDBContainer.start();
	}
	
	@Test
	void shouldCreateProduct() {
		//Multiline string introduced in JAVA 14
		String requestBody= """
				{
				"name":"iPhon15",
				"description":"iPhone15 is a smartphone from apple",
				"price":1000
				}
				""";
		
		RestAssured.given()
		.contentType("application/json")
		.body(requestBody)
		.when()
		.post("/api/product")
		.then()
		.statusCode(201)
		.body("id", Matchers.notNullValue())
		.body("name", Matchers.equalTo("iPhone15"));
		
	}

}
