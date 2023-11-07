package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductAndSellerDTO {
    private String productName;
    private String picture;
    private String description;
    private double price;
    private String category;
    private String measurement;
    private String name;
    private String surname;
    private String username;
    private String pictureOfSeller;
    private double longitude;
    private double latitude;
}
