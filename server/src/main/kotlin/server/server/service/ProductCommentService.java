package server.server.service;

import org.springframework.http.ResponseEntity;

public interface ProductCommentService {
    ResponseEntity<?> getProductCommentsByProductId(Long productId);
}
