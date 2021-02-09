package com.up42.up42backend.controller;

import com.up42.up42backend.model.Feature;
import com.up42.up42backend.service.FeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Base64;

@RestController
public class FeaturesController {

    @Autowired
    private FeaturesService featuresService;

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feature> getFeatures() {
         return featuresService.getFeatures();
    }

    @GetMapping(value = "/features/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Feature getFeature(@PathVariable String id) {
        Feature feature = this.featuresService.getFeatureById(id);

        if (feature == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,String.format("Feature id=%s not found", id)
            );
        }

        return feature;
    }

    @GetMapping(value = "/features/{id}/quicklook", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFeatureOutlook(@PathVariable String id) {
        Feature feature = this.getFeature(id);

        String quicklook = this.featuresService.getQuicklook(id);
        if (quicklook == null) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,String.format("Feature id=%s does not contains quicklook", id)
            );
        }

        return Base64.getDecoder().decode(quicklook);
    }
}
