package server.server.service.Impl;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.CommentDTO;
import server.server.exceptions.InvalidProductIdException;
import server.server.models.ProductComment;
import server.server.repository.ProductCommentRepository;
import server.server.service.ProductCommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCommentServiceImpl implements ProductCommentService {

    @Autowired
    ProductCommentRepository productCommentRepository;

    @SneakyThrows
    @Override
    public ResponseEntity<?> getProductCommentsByProductId(Long productId) {
        List<ProductComment> productComments = productCommentRepository.findByProduct_ProductId(productId);

        List<CommentDTO> comments = new ArrayList<>();
        for (ProductComment p : productComments) {
            CommentDTO commentDTO = CommentDTO.builder()
                    .date(p.getDate())
                    .grade(p.getGrade())
                    .text(p.getText())
                    .name(p.getUser().getName())
                    .surname(p.getUser().getSurname())
                    .username(p.getUser().getUsername())
                    .picture(p.getUser().getPicture())
                    .build();

            comments.add(commentDTO);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
