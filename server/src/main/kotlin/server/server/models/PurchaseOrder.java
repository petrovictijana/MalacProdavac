package server.server.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order orderId;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;
}
