package server.server.models;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;

    private String pib;
    private String address;

    private double longitude;
    private double latitude;

    public Seller() {
    }

    public Seller(String pib, String address, double longitude, double latitude) {
        this.pib = pib;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Seller(Long sellerId, String pib, String address, double longitude, double latitude) {
        this.sellerId = sellerId;
        this.pib = pib;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", pib='" + pib + '\'' +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
