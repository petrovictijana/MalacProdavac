package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.models.Seller;
import server.server.repository.SellerRepository;
import server.server.service.SellerService;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public Optional<Seller> getHouseholdById(Long id) {
        return sellerRepository.findById(id);
    }
}
