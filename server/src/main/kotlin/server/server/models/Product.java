package server.server.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @JoinColumn(name = "seller_id")
    @ManyToOne
    private Seller seller;

    @Column(name="product_name")
    private String productName;
    private String picture;
    private String description;
    private double price;

    @ManyToOne()
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name="measurement_id")
    private Measurement measurement;

}
