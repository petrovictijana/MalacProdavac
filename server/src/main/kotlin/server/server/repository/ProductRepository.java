package server.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.server.dtos.SellerDTO;
import server.server.models.Product;
import server.server.models.Seller;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long productId);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    @Query("SELECT p.seller FROM Product p WHERE p.productId = :productId")
    Seller findSellerByProductId(Long productId);

    @Query("SELECT p FROM Product p WHERE " +
            "p.productName LIKE CONCAT('%',:query, '%')")
    List<Product> searchProduct(String query);
}
