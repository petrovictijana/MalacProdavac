package server.server.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class TopSellersMonth {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private double longitude;
    private double latitude;
    private long numberOfOrders;
}
