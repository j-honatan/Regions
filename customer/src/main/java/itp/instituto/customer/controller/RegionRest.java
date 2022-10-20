package itp.instituto.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itp.instituto.customer.entity.Customer;
import itp.instituto.customer.entity.Region;
import itp.instituto.customer.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/regions")

public class RegionRest {
    @Autowired
    RegionService regionService;

    // -------------------Recupera todas las Regiones---

    @GetMapping
    public ResponseEntity<List<Region>> listAllRegions(@RequestParam(name = "customerId", required = false) Long customerId) {
        List<Region> regions = new ArrayList<>();
        if (null == customerId) {
            regions = regionService.findRegionAll();
            if (regions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            if (null == regions) {
                log.error("Regions with Customer id {} not found.", customerId);
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(regions);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable("id") long id){
        log.info("Fetching Region with id {}", id);
        Region region = regionService.getRegion(id);
        if (null == region){
            log.error("Region with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    public ResponseEntity<Region> createRegion( @RequestBody Region region , BindingResult result) {
        log.info("Creating Region : {}", region);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Region regionDB = regionService.createRegion (region);

        return  ResponseEntity.status( HttpStatus.CREATED).body(regionDB);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateRegion(@PathVariable("id") long id, @RequestBody Region region) {
        log.info("Updating Region with id {}", id);

        Region currentRegion = regionService.getRegion(id);

        if ( null == currentRegion ) {
            log.error("Unable to update. Region with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        region.setId(id);
        currentRegion=regionService.updateRegion(region);
        return  ResponseEntity.ok(currentRegion);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Region> deleteRegion(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Region with id {}", id);

        Region region = regionService.getRegion(id);
        if (null == region){
            log.error("Unable to delete. Region with id {} not found.", id);
            return ResponseEntity.notFound().build();
        }
        region = regionService.deleteRegion(region);
        return ResponseEntity.ok(region);
    }





    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }



} //final
