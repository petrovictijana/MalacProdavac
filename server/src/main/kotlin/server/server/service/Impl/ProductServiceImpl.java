package server.server.service.Impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.CommentDTO;
import server.server.dtos.ProductDTO;
import server.server.dtos.ProductSellerCommentDTO;
import server.server.dtos.SellerDTO;
import server.server.exceptions.InvalidProductIdException;
import server.server.models.Product;
import server.server.models.ProductComment;
import server.server.models.Seller;
import server.server.repository.ProductCommentRepository;
import server.server.repository.ProductRepository;
import server.server.service.ProductService;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCommentRepository productCommentRepository;

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            ProductDTO productDTO = null;
            return new ResponseEntity<>(productDTO,HttpStatus.OK);
        }

        ProductDTO productDTO = ProductDTO.builder()
                .productName(product.get().getProductName())
                .category(product.get().getCategory().getName())
                .description(product.get().getDescription())
                .sellerName(product.get().getSeller().getUser().getName())
                .price(product.get().getPrice())
                .picture(product.get().getPicture())
                .measurement(product.get().getMeasurement().getName())
                .build();

        SellerDTO sellerDTO = SellerDTO.builder()
                .latitude(product.get().getSeller().getLatitude())
                .longitude(product.get().getSeller().getLongitude())
                .pib(product.get().getSeller().getPib())
                .adress(product.get().getSeller().getAddress())
                .surname(product.get().getSeller().getUser().getSurname())
                .name(product.get().getSeller().getUser().getName())
                .username(product.get().getSeller().getUser().getUsername())
                .email(product.get().getSeller().getUser().getEmail())
                .picture(product.get().getSeller().getUser().getPicture())
                .build();


        List<ProductComment> productComments = productCommentRepository.findByProduct_ProductId(id);
        List<CommentDTO> comments = new ArrayList<>();

        if(!productComments.isEmpty()) {

            List<ProductComment> randomComments = getRandomComments(productComments);

            for (ProductComment p : randomComments) {
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
        }

        ProductSellerCommentDTO productSellerCommentDTO = new ProductSellerCommentDTO(sellerDTO,productDTO,comments);

        return new ResponseEntity<>(productSellerCommentDTO,HttpStatus.OK);


    }

    private List<ProductComment> getRandomComments(List<ProductComment> allComments) {

        int numberOfCommentsForProduct = allComments.size();

        int number;
        Date date = new Date();

        if(date.getDay()%2==0){
            number = Math.min(3,numberOfCommentsForProduct);
        }else {
            number = Math.min(5,numberOfCommentsForProduct);
        }

        return allComments.subList(0,number);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<?> getSellerByProductId(Long productId) {
        Seller seller = productRepository.findSellerByProductId(productId);
        if(seller == null){
            throw new InvalidProductIdException("Ovakav proizvodjac ne postoji");
        }
        return new ResponseEntity<>(SellerDTO.builder()
                .name(seller.getUser().getName())
                .username(seller.getUser().getUsername())
                .surname(seller.getUser().getSurname())
                .email(seller.getUser().getUsername())
                .picture(seller.getUser().getPicture())
                .pib(seller.getPib())
                .adress(seller.getAddress())
                .longitude(seller.getLongitude())
                .latitude(seller.getLatitude())
                .build(),HttpStatus.OK);
    }


}
