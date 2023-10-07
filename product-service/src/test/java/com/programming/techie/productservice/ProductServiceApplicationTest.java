package com.programming.techie.productservice;

import com.programming.techie.productservice.dto.ProductRequest;
import com.programming.techie.productservice.dto.ProductResponse;
import com.programming.techie.productservice.repository.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductServiceApplicationTest {
      @Container
      static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.2");

      @Autowired
      private MockMvc mockMvc;

      @Autowired
      private ObjectMapper objectMapper;

      @Autowired
     ProductRepository productRepository;


      @DynamicPropertySource
     static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
     dynamicPropertyRegistry.add("spring.data.momgo.uri",mongoDBContainer::getReplicaSetUrl);
      }

      @Test
     public void shouldCreateProduct() throws Exception {
          ProductRequest productRequest =   getProductRequest();
         String productRequestString =  objectMapper.writeValueAsString(productRequest);

          mockMvc.perform(MockMvcRequestBuilders.post("/api/product/add").
                  contentType(MediaType.APPLICATION_JSON).
                  content(productRequestString) ).
                  andExpect(status().isCreated());
          Assertions.assertEquals(1,productRepository.findAll().size());

      }

      private ProductRequest getProductRequest(){
          return ProductRequest.builder().name("iphone 13").description("iphone 13").price(BigDecimal.valueOf(1200)).build();
      }



}
