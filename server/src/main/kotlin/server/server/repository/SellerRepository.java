package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.server.models.Seller;
import server.server.models.User;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
        Seller findByPib(String pib);
}
