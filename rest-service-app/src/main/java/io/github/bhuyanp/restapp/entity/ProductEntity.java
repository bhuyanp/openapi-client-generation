package io.github.bhuyanp.restapp.entity;

import io.github.bhuyanp.restapp.dto.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy=UUID)
    @Column(name="id")
    private UUID id;
    @Column(name="title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private Product.TYPE type ;
    @Column(name="price")
    private double price;
    @Column(name="created_date")
    private LocalDateTime createdDate;
    @Column(name="updated_date")
    private LocalDateTime updatedDate;
}
