package itp.instituto.customer.repository;

import itp.instituto.customer.entity.Customer;
import itp.instituto.customer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    public Region findByName(String name);

}
