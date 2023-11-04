package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.server.service.SearchService;

@RestController
@RequestMapping
public class SearchController {

    @Autowired
    SearchService searchService;
    @GetMapping("/search")
    public ResponseEntity<?> searchProductsAndSellers(@RequestParam("query") String query){
        return searchService.searchProductsAndSellers(query);
    }

}
