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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id",nullable = false)
    private long orderId;

    //private long order_status_id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    //private long user_id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "buyer_id")
    private User buyer;

    //private long seller_id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "seller_id")
    private Seller seller;

    private double longitudeBuyer;
    private double latitudeBuyer;
    private Date orderDate;
}
