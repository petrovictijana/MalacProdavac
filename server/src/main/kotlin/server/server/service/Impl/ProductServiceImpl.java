package server.server.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.SellerDTO;
import server.server.exceptions.InvalidProductIdException;
import server.server.models.Product;
import server.server.models.Seller;
import server.server.repository.ProductRepository;
import server.server.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;



    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> getSellerByProductId(Long productId) {
        Seller seller = productRepository.findSellerByProductId(productId);
        if(seller == null){
            throw new InvalidProductIdException("Ovakav proizvod ne postoji");
        }
        return new ResponseEntity<>(SellerDTO.builder()
                .name(seller.getUser().getName())
                .username(seller.getUser().getUsername())
                .surname(seller.getUser().getSurname())
                .email(seller.getUser().getUsername())
                .picture(seller.getUser().getPicture())
                .pib(seller.getPib())
                .adress(seller.getAddress()).build(),HttpStatus.OK);
    }


}
