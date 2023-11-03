package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server.models.ProductComment;
import server.server.service.ProductCommentService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping
public class ProductCommentController {

    @Autowired
    ProductCommentService productCommentService;

    @GetMapping("/comments/{productId}")
    public  List<ProductComment> getAllCommentsForProduct(@PathVariable Long productId){
        return productCommentService.getProductCommentsByProductId(productId);
    }

}
