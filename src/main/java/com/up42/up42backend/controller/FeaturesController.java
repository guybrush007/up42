package com.up42.up42backend.controller;

import com.up42.up42backend.model.Feature;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FeaturesController {

    @GetMapping(value = "/features", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feature> getFeatures() {
         return new ArrayList<>();
    }

    @GetMapping(value = "/features/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Feature getFeature(@PathVariable String id) {
        return new Feature(
                "id",
                123l,
                456l,
                789l,
                "missionName"
        );
    }

    @GetMapping(value = "/features/{id}/quicklook", produces = MediaType.APPLICATION_JSON_VALUE)
    public byte[] getFeatureOutlook(@PathVariable String id) {
        return new byte[]{1, 2 ,3};
    }
}
