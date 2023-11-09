package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductDTO {
    private String sellerName;
    private String productName;
    private String picture;
    private String description;
    private double price;
    private String category;
    private String measurement;
}
