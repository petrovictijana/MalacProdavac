package server.server.service;

import server.server.models.Product;
import server.server.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(Long id);

    List<Product> getProductByName(String name);

    Seller getSeller(Long productId);
}
