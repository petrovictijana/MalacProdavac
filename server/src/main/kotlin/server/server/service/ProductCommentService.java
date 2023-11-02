package server.server.service;

import server.server.models.ProductComment;
import server.server.models.Seller;

import java.util.List;

public interface ProductCommentService {
    List<ProductComment> getProductCommentsByProductId(Long productId);
}
