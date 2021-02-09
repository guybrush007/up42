package com.up42.up42backend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.up42.up42backend.model.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FeaturesServiceTest {
    private FeaturesService service;

    private Feature testFeature;

    @BeforeEach
    public void setUp() {
        this.service = new FeaturesService();
        this.testFeature = new Feature(
                "39c2f29e-c0f8-4a39-a98b-deed547d6aea",
                1554831167697l,
                1554831167697l,
                1554831202043l,
                "Sentinel-1B",
                "iVBORw0KGgoAAAANSUhEUgAAAgAAAAHRCAIAAACT"
        );
    }

    @Test
    public void testGetFeatureById_whenValidId_returnsFeature() {
        Feature feature = service.getFeatureById(this.testFeature.getId());
        assertNotNull(feature);
        assertEquals(this.testFeature.getId(), feature.getId());
        assertEquals(this.testFeature.getTimestamp(), feature.getTimestamp());
        assertEquals(this.testFeature.getBeginViewingDate(), feature.getBeginViewingDate());
        assertEquals(this.testFeature.getEndViewingDate(), feature.getEndViewingDate());
        assertEquals(this.testFeature.getMissionName(), feature.getMissionName());
        assertTrue(feature.getQuicklook().startsWith(testFeature.getQuicklook()));
    }

    @Test
    public void testGetFeatureById_whenInvalidId_returnsNull() {
        Feature feature = service.getFeatureById("invalid");
        assertNull(feature);
    }

    @Test
    public void testGetFeatureById_whenNullId_returnsNull() {
        Feature feature = service.getFeatureById(null);
        assertNull(feature);
    }

    @Test
    public void testGetFeatures() {
        List<Feature> features = service.getFeatures();
        assertEquals(14, features.size());
    }
}