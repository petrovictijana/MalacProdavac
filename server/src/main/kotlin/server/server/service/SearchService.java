package server.server.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

public interface SearchService {

    ResponseEntity<?> searchProductsAndSellers(String query);
}
