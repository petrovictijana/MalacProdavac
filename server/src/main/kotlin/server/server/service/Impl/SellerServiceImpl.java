package server.server.service.Impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.SellerDTO;
import server.server.exceptions.InvalidSellerIdException;
import server.server.models.Seller;
import server.server.repository.SellerRepository;
import server.server.service.SellerService;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @SneakyThrows
    @Override
    public ResponseEntity<?> getHouseholdById(Long sellerId) {
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        System.out.println(seller);

        if(seller.isEmpty()){
            throw new InvalidSellerIdException("Proizcodjac sa ovim id-om ne postoji!");
        }

        return new ResponseEntity<>(SellerDTO.builder()
                .name(seller.get().getUser().getName())
                .username(seller.get().getUser().getUsername())
                .surname(seller.get().getUser().getSurname())
                .email(seller.get().getUser().getEmail())
                .picture(seller.get().getUser().getPicture())
                .pib(seller.get().getPib())
                .adress(seller.get().getAddress()).build(), HttpStatus.OK);
    }
}
