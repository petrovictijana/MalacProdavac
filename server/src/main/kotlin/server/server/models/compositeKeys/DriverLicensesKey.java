package server.server.models.compositeKeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class DriverLicensesKey implements Serializable {
    private Long userId;
    private Long drivingCategoryId;
}
