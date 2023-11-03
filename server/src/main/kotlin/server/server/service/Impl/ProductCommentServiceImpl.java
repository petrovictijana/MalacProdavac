package server.server.service.Impl;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.exceptions.InvalidProductIdException;
import server.server.models.ProductComment;
import server.server.repository.ProductCommentRepository;
import server.server.service.ProductCommentService;

import java.util.List;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    ProductCommentRepository productCommentRepository;

    @SneakyThrows
    @Override
    public List<ProductComment> getProductCommentsByProductId(Long productId) {
        List<ProductComment> productComments = productCommentRepository.findByProduct_ProductId(productId);

        if(productComments == null){
            throw new InvalidProductIdException("Komenari za ovaj prozivod ne postoje");
        }

        return productComments;
    }

}
