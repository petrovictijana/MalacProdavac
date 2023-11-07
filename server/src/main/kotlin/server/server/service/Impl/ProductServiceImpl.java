package server.server.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.server.dtos.CommentDTO;
import server.server.dtos.ProductAndSellerDTO;
import server.server.dtos.ProductWithCommentsDTO;
import server.server.dtos.SellerDTO;
import server.server.exceptions.InvalidProductIdException;
import server.server.models.Product;
import server.server.models.ProductComment;
import server.server.models.Seller;
import server.server.repository.ProductCommentRepository;
import server.server.repository.ProductRepository;
import server.server.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            throw new InvalidProductIdException("Ovakav proizvod ne postoji");
        }

        ProductAndSellerDTO productAndSellerDTO = ProductAndSellerDTO.builder()
                .pictureOfSeller(product.get().getSeller().getUser().getPicture())
                .picture(product.get().getPicture())
                .price(product.get().getPrice())
                .measurement(product.get().getMeasurement().getName())
                .description(product.get().getDescription())
                .productName(product.get().getProductName())
                .latitude(product.get().getSeller().getLatitude())
                .longitude(product.get().getSeller().getLongitude())
                .username(product.get().getSeller().getUser().getUsername())
                .name(product.get().getSeller().getUser().getName())
                .surname(product.get().getSeller().getUser().getSurname())
                .category(product.get().getCategory().getName())
                .description(product.get().getDescription())
                .build();

        List<ProductComment> productComments = productCommentRepository.findByProduct_ProductId(id);

        if(productComments.isEmpty()){
            throw new InvalidProductIdException("Komenari za ovaj prozivod ne postoje");
        }

        List<ProductComment> randomComments = getRandomComments(productComments);

        List<CommentDTO> comments = new ArrayList<>();
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

        ProductWithCommentsDTO productWithCommentsDTO = new ProductWithCommentsDTO(productAndSellerDTO,comments);
        return new ResponseEntity<>(productWithCommentsDTO,HttpStatus.OK);


    }

    private List<ProductComment> getRandomComments(List<ProductComment> allComments) {
        int numComments= Math.min(5, allComments.size()); // Ograničite na najviše 5 komentara
        Collections.shuffle(allComments); // Nasumično pomiješajte komentare
        return allComments.subList(0, numComments); // Odaberite prva tri do pet komentara
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
            throw new InvalidProductIdException("Ovakav proizvod ne postoji");
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
