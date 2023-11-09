package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.CommentDTO;
import server.server.dtos.ProductDTO;
import server.server.dtos.SellerDTO;
import server.server.exceptions.InvalidSearchException;
import server.server.models.Product;
import server.server.models.ProductComment;
import server.server.models.Seller;
import server.server.repository.ProductRepository;
import server.server.repository.SellerRepository;
import server.server.service.SearchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Override
    public ResponseEntity<?> searchProductsAndSellers(String query) {
        HashMap<String, List<?>> productsAndSellers = new HashMap<>();
        List<Product> products = productRepository.searchProduct(query);
        List<Seller> sellers = sellerRepository.searchSellers(query);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productsAndSellers.put("products",productDTOS);

        List<SellerDTO> sellerDTOS = new ArrayList<>();
        productsAndSellers.put("seller",sellerDTOS);

        if(!products.isEmpty()){
            for (Product p : products) {
                ProductDTO productDTO = ProductDTO.builder()
                        .productName(p.getProductName())
                        .picture(p.getPicture())
                        .category(p.getCategory().getName())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .measurement(p.getMeasurement().getName())
                        .sellerName(p.getSeller().getUser().getName())
                        .build();

                productDTOS.add(productDTO);
            }
        }


        if(!sellers.isEmpty()){
            for (Seller s : sellers) {
                SellerDTO sellerDTO = SellerDTO.builder()
                        .name(s.getUser().getName())
                        .username(s.getUser().getUsername())
                        .surname(s.getUser().getSurname())
                        .email(s.getUser().getEmail())
                        .picture(s.getUser().getPicture())
                        .pib(s.getPib())
                        .adress(s.getAddress()).build();

                sellerDTOS.add(sellerDTO);
            }

        }

        return new ResponseEntity<>(productsAndSellers, HttpStatus.OK);
    }
}
