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
    private String productPicture;
    private String sellerUsername;
    private Double longitude;
    private Double latitude;
    private Long soldItems;
}
