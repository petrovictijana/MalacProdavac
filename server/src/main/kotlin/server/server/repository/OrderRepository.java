package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.server.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
