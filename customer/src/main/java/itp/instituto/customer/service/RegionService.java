package itp.instituto.customer.service;

import itp.instituto.customer.entity.Customer;
import itp.instituto.customer.entity.Region;

import java.util.List;

public interface RegionService {

    public List<Region> findRegionAll();


    public Region createRegion(Region region);

    public Region updateRegion(Region region);
    public Region deleteRegion(Region region);

    public Region getRegion (Long id);
}
