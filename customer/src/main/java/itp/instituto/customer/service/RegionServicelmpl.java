package itp.instituto.customer.service;


import itp.instituto.customer.entity.Customer;
import itp.instituto.customer.entity.Region;
import itp.instituto.customer.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import java.util.List;

@Slf4j
@Service
public class RegionServicelmpl implements RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<Region> findRegionAll() { return regionRepository.findAll(); }


    @Override
    public Region createRegion(Region region) {


        Region regionDB = regionRepository.findByName(region.getName());

        if (regionDB != null){
            return  regionDB;
        }

        region.setState("CREATED");
        regionDB = regionRepository.save(region);

       return regionDB;

    }

    @Override
    public Region updateRegion(Region region) {

        Region regionDB = getRegion(region.getId());
        if (regionDB == null){
            return  null;
        }
        regionDB.setName(region.getName());

        return  regionRepository.save(regionDB);
    }

    @Override
    public Region deleteRegion(Region region) {
        Region regionDB = getRegion(region.getId());
        if (regionDB == null){
            return null;
        }
        region.setState("DELETE");
        return regionRepository.save(region);
    }


    @Override
    public Region getRegion(Long id) {
        return regionRepository.findById(id).orElse(null);
    }
}
