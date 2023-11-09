package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.dtos.SellerDTO;
import server.server.models.Product;
import server.server.models.Seller;
import server.server.service.ProductService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return productService.getProductById(id);

    }

    @GetMapping("/getSellerByProductId/{id}")
    public ResponseEntity<?> getSellerByProductId(@PathVariable Long id){
       return productService.getSellerByProductId(id);
    }


}
