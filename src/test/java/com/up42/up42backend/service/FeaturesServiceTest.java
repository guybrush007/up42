package com.up42.up42backend.service;

import com.up42.up42backend.model.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FeaturesServiceTest {
    private FeaturesService service;

    @BeforeEach
    public void setUp() {
        this.service = new FeaturesService();
    }

    @Test
    public void testGetFeatureById() {
        Feature feature = service.getFeatureById("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
        Assertions.assertNotNull(feature);
    }

    @Test
    public void testGetFeatures() {
        List<Feature> features = service.getFeatures();
        Assertions.assertEquals(14, features.size());
    }
}