package server.server.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopProductsMonth {
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private Long productCategoryId;
    private Long productMeasurementId;
    private String sellerName;
    private String sellerSurname;
    private String sellerUsername;
    private Long soldItems;
}
