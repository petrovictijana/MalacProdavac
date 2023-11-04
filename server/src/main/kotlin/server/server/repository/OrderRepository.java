package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.server.models.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT distinct u.user_id as id, u.name as name, u.surname as surname, u.username as username, s.longitude as longitude, s.latitude as latitude, jt.numberOfOrders as numberOfOrders\n" +
            "FROM users u JOIN sellers s JOIN (\n" +
            "\tSELECT seller_id, count(seller_id) as numberOfOrders\n" +
            "\tFROM orders\n" +
            "\tWHERE month(order_date) = month(now())\n" +
            "\tGROUP BY seller_id\n" +
            "\tORDER BY count(seller_id) DESC \n" +
            "\tLIMIT 3\n" +
            ") as jt ON u.user_id = s.seller_id AND u.user_id = jt.seller_id\n" +
            "ORDER BY jt.numberOfOrders DESC", nativeQuery = true)
    List<Object[]> getTop3SellersOfTheMonth();

    @Query(value = "SELECT p.product_id as productId, p.product_name as productName, p.picture as productPicture, u.username as sellerUsername, s.longitude as sellerLongitude, s.latitude as sellerLatitude, products_top.soldItems as soldItems\n" +
            "FROM products p JOIN sellers s ON p.seller_id = s.seller_id JOIN users u ON u.user_id = s.seller_id JOIN\n" +
            "(SELECT product_id, count(product_id) as soldItems\n" +
            "FROM purchase_order\n" +
            "WHERE order_id in (SELECT order_id\n" +
            "FROM orders\n" +
            "WHERE month(order_date) = month(now()))\n" +
            "GROUP BY product_id\n" +
            "LIMIT 3) as products_top ON p.product_id = products_top.product_id\n" +
            "ORDER BY products_top.soldItems DESC;", nativeQuery = true)
    List<Object[]> getTop3ProductsOfTheMonth();


}
