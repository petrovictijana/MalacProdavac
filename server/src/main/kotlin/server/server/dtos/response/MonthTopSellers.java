package server.server.dtos.response;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MonthTopSellers {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private double longitude;
    private double latitude;
    private long numberOfOrders;
}
