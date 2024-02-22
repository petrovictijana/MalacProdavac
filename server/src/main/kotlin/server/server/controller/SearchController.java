package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.service.SearchService;

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    SearchService searchService;
    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchProductsAndSellers(@PathVariable String query){
        return searchService.searchProductsAndSellers(query);
    }

}
