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


}
