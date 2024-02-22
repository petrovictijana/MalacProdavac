package server.server.service;

import org.springframework.http.ResponseEntity;

public interface SellerService {
    ResponseEntity<?> getHouseholdById(Long id);
}
