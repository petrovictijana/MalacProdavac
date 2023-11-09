package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SellerDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String picture;
    private String pib;
    private String adress;
    private double longitude;
    private double latitude;

}
