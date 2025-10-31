package io.github.bhuyanp.restapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("unitTest")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    static List<Product> products;

    static final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    static void setup(){
        // given
        LocalDateTime createdDate1 = LocalDateTime.of(2025,1,1,12,0,0);
        LocalDateTime createdDate2 = LocalDateTime.of(2024,1,1,12,0,0);
        // when
        products = List.of(
                new Product("id1", "title1", Product.TYPE.ELECTRONICS, 12.12, createdDate1, null),
                new Product("id2", "title2", Product.TYPE.BEAUTY, 22.12, createdDate2, null)
        );
    }

    @BeforeEach
    void beforeEach(){
        // given
        // when
        when(productService.getProducts()).thenReturn(products);
        when(productService.getProduct("id1")).thenReturn(products.getFirst());
        when(productService.addProduct(new ProductRequest("title1", Product.TYPE.ELECTRONICS, 12.12))).thenReturn(products.getFirst());
        when(productService.updateProduct("id2",new ProductRequest("title1", Product.TYPE.ELECTRONICS, 12.12))).thenReturn(products.getFirst());
        doNothing().when(productService).deleteProduct("id1");
    }

    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get(ProductController.PRODUCT_API_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].type").value("ELECTRONICS"))
                .andExpect(jsonPath("$[0].price").value(12.12))
                .andExpect(jsonPath("$[0].createdDate").value("2025-01-01T12:00:00"))
                .andExpect(jsonPath("$[1].id").value("id2"))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].type").value("BEAUTY"))
                .andExpect(jsonPath("$[1].price").value(22.12))
                .andExpect(jsonPath("$[1].createdDate").value("2024-01-01T12:00:00"));

    }


    @Test
    void getProduct() throws Exception {
        mockMvc.perform(get(ProductController.PRODUCT_API_PATH+"/id1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("id1"))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.type").value("ELECTRONICS"))
                .andExpect(jsonPath("$.price").value(12.12))
                .andExpect(jsonPath("$.createdDate").value("2025-01-01T12:00:00"));

    }

    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("title1", Product.TYPE.ELECTRONICS, 12.12);

        mockMvc.perform(post(ProductController.PRODUCT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("id1"))
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.type").value("ELECTRONICS"))
                .andExpect(jsonPath("$.price").value(12.12))
                .andExpect(jsonPath("$.createdDate").value("2025-01-01T12:00:00"));

    }

    @Test
    void addProduct_shouldFailWithBadRequest() throws Exception {
        ProductRequest productRequest = new ProductRequest(null, Product.TYPE.ELECTRONICS, 12.12);

        mockMvc.perform(post(ProductController.PRODUCT_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("title1", Product.TYPE.ELECTRONICS, 12.12);

        MockHttpServletRequestBuilder putRequestBuilder =
                MockMvcRequestBuilders.put(ProductController.PRODUCT_API_PATH+"/id1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(productRequest));
        mockMvc.perform(putRequestBuilder)
                .andExpect(status().isNoContent());

    }

    @Test
    void updateProduct_shouldFailWithBadRequest() throws Exception {
        ProductRequest productRequest = new ProductRequest(null, Product.TYPE.ELECTRONICS, 12.12);

        MockHttpServletRequestBuilder putRequestBuilder =
                MockMvcRequestBuilders.put(ProductController.PRODUCT_API_PATH+"/id1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(productRequest));
        mockMvc.perform(putRequestBuilder)
                .andExpect(status().isBadRequest());

    }

    @Test
    void partialUpdateProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest(null,Product.TYPE.ELECTRONICS, 12.12);

        MockHttpServletRequestBuilder putRequestBuilder =
                MockMvcRequestBuilders.patch(ProductController.PRODUCT_API_PATH+"/id1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(productRequest));
        mockMvc.perform(putRequestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct() throws Exception {
        MockHttpServletRequestBuilder putRequestBuilder =
                MockMvcRequestBuilders.delete(ProductController.PRODUCT_API_PATH+"/id1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8);
        mockMvc.perform(putRequestBuilder)
                .andExpect(status().isNoContent());
    }
}