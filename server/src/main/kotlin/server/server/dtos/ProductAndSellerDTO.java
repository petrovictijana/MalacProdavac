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
    private String sellerName;
    private String sellerSurname;
    private String sellerUsername;
    private String pictureOfSeller;
    private double sellerLongitude;
    private double sellerLatitude;
}
