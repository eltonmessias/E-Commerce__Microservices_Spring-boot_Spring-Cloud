package com.eltonmessias.ecommerce.product;

import com.eltonmessias.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private double price;
    private int availableQuantity;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
