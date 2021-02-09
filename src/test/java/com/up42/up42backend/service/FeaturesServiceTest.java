package com.up42.up42backend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.up42.up42backend.model.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FeaturesServiceTest {
    public static final String TEST_QUICK_LOOK_START = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAHRCAIAAACT";
    public static final String FEATURE_ID_WITHOUT_QUICKLOOK = "b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5";
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
                "Sentinel-1B"
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
    }

    @Test
    public void testGetFeatureById_whenUnknownId_returnsNull() {
        Feature feature = service.getFeatureById("unknown");
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

    @Test
    public void testGetQuicklook_whenCalledWithIdContainingQuicklook_returnsString(){
        String quicklook = service.getQuicklook(this.testFeature.getId());
        assertTrue(quicklook.startsWith(TEST_QUICK_LOOK_START));
    }

    @Test
    public void testGetQuicklook_whenCalledWithIdNotContainingQuicklook_returnsNull(){
        String quicklook = service.getQuicklook(FEATURE_ID_WITHOUT_QUICKLOOK);
        assertNull(quicklook);
    }

    @Test
    public void testGetQuicklook_whenCalledWithUnknownId_returnsNull(){
        String quicklook = service.getQuicklook("unknown");
        assertNull(quicklook);
    }

    @Test
    public void testGetQuicklook_whenCalledWithNullId_returnsNull(){
        String quicklook = service.getQuicklook(null);
        assertNull(quicklook);
    }
}