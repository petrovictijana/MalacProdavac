package server.server.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private long orderId;

    private long orderStatusId;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    private long BuyerId;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private User buyer;

    private long SellerId;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private double longitudeBuyer;
    private double latitudeBuyer;
    private Date orderDate;

}
