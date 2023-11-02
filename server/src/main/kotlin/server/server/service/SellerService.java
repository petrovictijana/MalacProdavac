package server.server.service;

import server.server.models.Seller;

import java.util.Optional;

public interface SellerService {
    Optional<Seller> getHouseholdById(Long id);
}
