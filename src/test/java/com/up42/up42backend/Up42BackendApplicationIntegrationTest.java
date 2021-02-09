package com.up42.up42backend;

import static org.junit.jupiter.api.Assertions.*;

import com.up42.up42backend.model.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Up42BackendApplicationIntegrationTest {
    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testGetFeatures_returnsAllFeatures() {
        ResponseEntity<Feature[]> response = restTemplate.getForEntity(baseUrl + "/features", Feature[].class);
        assertNotNull(response.getBody());
        assertEquals(14, ((Feature[])response.getBody()).length);
    }

    @Test
    public void testGetFeatureById_returnsFeature() {
        ResponseEntity<Feature> response = restTemplate.getForEntity(baseUrl + "/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea", Feature.class);
        Feature feature = response.getBody();
        assertNotNull(feature);
        assertEquals("39c2f29e-c0f8-4a39-a98b-deed547d6aea" , feature.getId());
        assertEquals(1554831167697L , feature.getTimestamp());
        assertEquals(1554831167697L, feature.getBeginViewingDate());
        assertEquals(1554831202043L, feature.getEndViewingDate());
        assertEquals("Sentinel-1B", feature.getMissionName());
    }

    @Test
    public void testGetFeatureById_whenInvalidId_returns404() {
        try {
            restTemplate.getForEntity(baseUrl + "/features/invalid", Feature.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    public void testGetFeatureQuickLook_returnsImage() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(baseUrl + "/features/39c2f29e-c0f8-4a39-a98b-deed547d6aea/quicklook", byte[].class);
        byte[] image = response.getBody();
        assertNotNull(image);
        try {
            ImageIO.read(new ByteArrayInputStream(image));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFeatureQuickLook_whenFeatureHasNoQuicklook_returns204() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(baseUrl + "/features/b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5/quicklook", byte[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
