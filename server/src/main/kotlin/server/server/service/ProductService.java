package server.server.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import server.server.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Long id);

    List<Product> getProductByName(String name);

    @Query("SELECT p.seller FROM Product p WHERE p.productId= :prodcutId")
    ResponseEntity<?> getSellerByProductId(Long productId);
}
