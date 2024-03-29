package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.server.models.Seller;
import server.server.service.SellerService;

import java.util.Optional;

@RestController
@RequestMapping
public class SellerController {
    @Autowired
    SellerService sellerService;

    @GetMapping("/getSeller/{id}")
    public ResponseEntity<?> getHouseholdById(@PathVariable Long id){
        return sellerService.getHouseholdById(id);
    }
}
