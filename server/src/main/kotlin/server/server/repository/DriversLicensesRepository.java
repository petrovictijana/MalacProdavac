package server.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.server.models.DriversLicenses;
import server.server.models.compositeKeys.DriverLicensesKey;

@Repository
public interface DriversLicensesRepository extends CrudRepository<DriversLicenses, DriverLicensesKey> {
}
