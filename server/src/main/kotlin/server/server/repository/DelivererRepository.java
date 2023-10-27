package server.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.server.models.Deliverer;

@Repository
public interface DelivererRepository extends CrudRepository<Deliverer, Long> {

}
