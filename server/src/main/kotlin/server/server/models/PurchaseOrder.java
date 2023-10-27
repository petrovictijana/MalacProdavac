package server.server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @Column(name="order_id")
    private Long orderId;

    @Id
    @Column(name="product_id")
    private Long productId;

    private int quantity;

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
