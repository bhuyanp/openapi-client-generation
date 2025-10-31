package io.github.bhuyanp.restapp.service;

import io.github.bhuyanp.restapp.dto.Product;
import io.github.bhuyanp.restapp.dto.ProductRequest;
import io.github.bhuyanp.restapp.entity.ProductEntity;
import io.github.bhuyanp.restapp.exception.DownstreamException;
import io.github.bhuyanp.restapp.repo.ProductsRepo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unitTest")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    ProductsRepo productsRepo;

    @InjectMocks
    ProductService productService;

    @Captor
    ArgumentCaptor<ProductEntity> productEntityArgumentCaptor;

    @Test
    void getProducts_shouldReturnProducts() {
        // given
        LocalDateTime createdDate1 = LocalDateTime.now().minusDays(1);
        LocalDateTime createdDate2 = LocalDateTime.now().minusDays(5);
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<ProductEntity> productEntityList = List.of(
                new ProductEntity(id1, "title1", Product.TYPE.ELECTRONICS, 12.12, createdDate1, null),
                new ProductEntity(id2, "title2", Product.TYPE.BEAUTY, 22.12, createdDate2, null)
        );
        List<Product> productsList = List.of(
                new Product(id1.toString(), "title1", Product.TYPE.ELECTRONICS, 12.12, createdDate1, null),
                new Product(id2.toString(), "title2", Product.TYPE.BEAUTY, 22.12, createdDate2, null)
        );

        // when
        when(productsRepo.findAll()).thenReturn(productEntityList);
        List<Product> products = productService.getProducts();

        // then
        assertThat(products).isNotEmpty()
                .containsAll(productsList);
    }

    @Test
    void addProduct_shouldAddAndReturnSameProduct() {
        // given
        LocalDateTime createdDate1 = LocalDateTime.now().minusDays(1);
        UUID id1 = UUID.randomUUID();
        ProductRequest productRequest = new ProductRequest("title1",  Product.TYPE.ELECTRONICS, 12.12);
        ProductEntity productEntity = new ProductEntity(id1,"title1",  Product.TYPE.ELECTRONICS, 12.12, createdDate1, null);

        // when
        when(productsRepo.save(any(ProductEntity.class))).thenReturn(productEntity);
        Product product = productService.addProduct(productRequest);

        // then
        verify(productsRepo,times(1)).save(productEntityArgumentCaptor.capture());
        assertThat(productEntityArgumentCaptor.getValue()).isNotNull()
                .hasFieldOrPropertyWithValue("title", "title1")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 12.12)
                .extracting("createdDate")
                .matches(date -> ((LocalDateTime)date).plusSeconds(2).isAfter(LocalDateTime.now()));
        assertThat(product).isNotNull()
                .hasFieldOrPropertyWithValue("id", id1.toString())
                .hasFieldOrPropertyWithValue("title", "title1")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 12.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate1)
                .hasFieldOrPropertyWithValue("updatedDate", null);
    }

    @Test
    void getProduct_shouldReturnProduct() {
        // given
        UUID id1 = UUID.randomUUID();
        LocalDateTime createdDate1 = LocalDateTime.now().minusDays(1);
        ProductEntity productEntity = new ProductEntity(id1, "title1", Product.TYPE.ELECTRONICS, 12.12, createdDate1, null);

        // when
        when(productsRepo.findById(id1)).thenReturn(Optional.of(productEntity));
        Product product = productService.getProduct(id1.toString());

        // then
        assertThat(product).isNotNull()
                .hasFieldOrPropertyWithValue("id", id1.toString())
                .hasFieldOrPropertyWithValue("title", "title1")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 12.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate1);
    }

    @Test
    void getProduct_shouldThrowDownstreamExceptionWhenNoProductFound() {
        // given
        UUID id1 = UUID.randomUUID();

        // when
        when(productsRepo.findById(id1)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(()->productService.getProduct(id1.toString()))
                .isInstanceOf(DownstreamException.class)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);
    }


    @Test
    void updateProduct_shouldUpdateAllFields() {
        // given
        LocalDateTime createdDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedDate = LocalDateTime.now();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        ProductRequest productRequest = new ProductRequest("title2",  Product.TYPE.ELECTRONICS, 22.12);
        ProductEntity existingProductEntity = new ProductEntity(id1,"title1",  Product.TYPE.ELECTRONICS, 12.12, createdDate, null);
        ProductEntity finalProductEntity = new ProductEntity(id2,"title2",  Product.TYPE.ELECTRONICS, 22.12, createdDate, updatedDate);

        // when
        when(productsRepo.findById(id1)).thenReturn(Optional.of(existingProductEntity));
        when(productsRepo.save(any(ProductEntity.class))).thenReturn(finalProductEntity);
        Product product = productService.updateProduct(id1.toString(),productRequest);

        // then
        verify(productsRepo,times(1)).save(productEntityArgumentCaptor.capture());
        assertThat(productEntityArgumentCaptor.getValue()).isNotNull()
                .hasFieldOrPropertyWithValue("id", id1)
                .hasFieldOrPropertyWithValue("title", "title2")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 22.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate)
                .extracting("updatedDate")
                .matches(date -> ((LocalDateTime)date).plusSeconds(2).isAfter(LocalDateTime.now()));

        assertThat(product).isNotNull()
                .hasFieldOrPropertyWithValue("id", id2.toString())
                .hasFieldOrPropertyWithValue("title", "title2")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 22.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate)
                .hasFieldOrPropertyWithValue("updatedDate", updatedDate);
    }

    @Test
    void updateProduct_shouldUpdatePartially() {
        // given
        LocalDateTime createdDate = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedDate = LocalDateTime.now();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        ProductRequest productRequest = new ProductRequest(null,  Product.TYPE.ELECTRONICS, 22.12);
        ProductEntity existingProductEntity = new ProductEntity(id1,"title1",  Product.TYPE.ELECTRONICS, 12.12, createdDate, null);
        ProductEntity finalProductEntity = new ProductEntity(id2,"title1",  Product.TYPE.ELECTRONICS, 22.12, createdDate, updatedDate);

        // when
        when(productsRepo.findById(id1)).thenReturn(Optional.of(existingProductEntity));
        when(productsRepo.save(any(ProductEntity.class))).thenReturn(finalProductEntity);
        Product product = productService.updateProduct(id1.toString(),productRequest);

        // then
        verify(productsRepo,times(1)).save(productEntityArgumentCaptor.capture());
        assertThat(productEntityArgumentCaptor.getValue()).isNotNull()
                .hasFieldOrPropertyWithValue("id", id1)
                .hasFieldOrPropertyWithValue("title", "title1")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 22.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate)
                .extracting("updatedDate")
                .matches(date -> ((LocalDateTime)date).plusSeconds(2).isAfter(LocalDateTime.now()));

        assertThat(product).isNotNull()
                .hasFieldOrPropertyWithValue("id", id2.toString())
                .hasFieldOrPropertyWithValue("title", "title1")
                .hasFieldOrPropertyWithValue("type", Product.TYPE.ELECTRONICS)
                .hasFieldOrPropertyWithValue("price", 22.12)
                .hasFieldOrPropertyWithValue("createdDate", createdDate)
                .hasFieldOrPropertyWithValue("updatedDate", updatedDate);
    }


    @Test
    void deleteProduct_shouldRemoveProduct() {
        // given
        LocalDateTime createdDate = LocalDateTime.now().minusDays(1);
        UUID id1 = UUID.randomUUID();
        ProductEntity existingProductEntity = new ProductEntity(id1,"title1",  Product.TYPE.ELECTRONICS, 12.12, createdDate, null);

        // when
        when(productsRepo.findById(id1)).thenReturn(Optional.of(existingProductEntity));
        doNothing().when(productsRepo).deleteById(id1);
        productService.deleteProduct(id1.toString());

        // then
        verify(productsRepo,times(1)).deleteById(any());
    }

}