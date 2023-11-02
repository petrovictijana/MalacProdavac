package server.server.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.models.ProductComment;
import server.server.models.Seller;
import server.server.repository.ProductCommentRepository;
import server.server.service.ProductCommentService;

import java.util.List;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    ProductCommentRepository productCommentRepository;

    @Override
    public List<ProductComment> getProductCommentsByProductId(Long productId) {
        return productCommentRepository.findByProduct_ProductId(productId);
    }

}
