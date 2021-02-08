package com.up42.up42backend;


import com.up42.up42backend.model.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Up42BackendApplicationIntegrationTest {
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetFeatures() {
        ResponseEntity<Feature[]> response = restTemplate.getForEntity("http://localhost:8080/features", Feature[].class);
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void testGetFeatureById() {
        ResponseEntity<Feature> response = restTemplate.getForEntity("http://localhost:8080/features/123", Feature.class);
        Feature feature = response.getBody();
        Assertions.assertNotNull(feature);
        Assertions.assertEquals("id" , feature.getId());
        Assertions.assertEquals(123l , feature.getTimestamp());
        Assertions.assertEquals(456l, feature.getBeginViewingDate());
        Assertions.assertEquals(789l, feature.getEndViewingDate());
        Assertions.assertEquals("missionName", feature.getMissionName());
    }

    @Test
    public void testGetFeatureQuickLook() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity("http://localhost:8080/features/123/quicklook", byte[].class);
        byte[] image = response.getBody();
        Assertions.assertNotNull(image);
        Assertions.assertArrayEquals(new byte[]{1, 2, 3}, image);
    }

}
