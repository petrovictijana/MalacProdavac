package server.server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import server.server.models.compositeKeys.DriverLicensesKey;

@Entity
@IdClass(DriverLicensesKey.class)
public class DriversLicenses {
    @Id
    @Column(name="user_id")
    private Long userId;

    @Id
    @Column(name = "driving_category_id")
    private Long drivingCategoryId;

    public DriversLicenses() {
    }

    public DriversLicenses(Long drivingCategoryId) {
        this.drivingCategoryId = drivingCategoryId;
    }

    public DriversLicenses(Long userId, Long drivingCategoryId) {
        this.userId = userId;
        this.drivingCategoryId = drivingCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDrivingCategoryId() {
        return drivingCategoryId;
    }

    public void setDrivingCategoryId(Long drivingCategoryId) {
        this.drivingCategoryId = drivingCategoryId;
    }
}
