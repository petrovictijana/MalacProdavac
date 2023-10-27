package server.server.models;

import jakarta.persistence.*;

@Entity
public class Deliverer {
    @Id
    @Column(name = "deliverer_id")
    private Long delivererId;

    private String location;
    private double longitude;
    private double latitude;

    public Deliverer() {
    }

    public Deliverer(String location, double longitude, double latitude) {
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Deliverer(Long delivererId, String location, double longitude, double latitude) {
        this.delivererId = delivererId;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(Long delivererId) {
        this.delivererId = delivererId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "Deliverer{" +
                "delivererId=" + delivererId +
                ", location='" + location + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
