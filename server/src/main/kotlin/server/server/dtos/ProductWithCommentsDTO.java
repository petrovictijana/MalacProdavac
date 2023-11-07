package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductWithCommentsDTO {
    private ProductAndSellerDTO productAndSeller;
    private List<CommentDTO> comments;

    public ProductWithCommentsDTO(ProductAndSellerDTO productAndSeller, List<CommentDTO> comments) {
        this.productAndSeller = productAndSeller;
        this.comments = comments;
    }
}
