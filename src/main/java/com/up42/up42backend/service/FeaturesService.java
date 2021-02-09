package com.up42.up42backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.up42backend.model.Feature;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service to access features data from default source data file.
 */
@Service
public class FeaturesService {
    private static Logger LOG = Logger.getLogger(FeaturesService.class.getName());
    private final static String DEFAULT_SOURCE_FILE = "source-data.json";
    private Map<String, Feature> features;

    public FeaturesService() {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(DEFAULT_SOURCE_FILE);

        this.features = new HashMap<>();
        try {
            JsonNode jsonNode = mapper.readTree(resource);
            for (int i = 0 ; i < jsonNode.size() ; i++) {
                Feature feature = extractFeatureFromJsonNode(jsonNode.get(i));
                features.put(feature.getId(), feature);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error while parsing source-data.json", e);
        }
    }

    private static Feature extractFeatureFromJsonNode(JsonNode jsonNode) {
        JsonNode featureNode = jsonNode.get("features").get(0);
        JsonNode propertiesNode = featureNode.get("properties");
        JsonNode acquisitionNode = propertiesNode.get("acquisition");
        JsonNode quickLookNode = propertiesNode.get("quicklook");

        return new Feature(
                propertiesNode.get("id").asText(),
                propertiesNode.get("timestamp").asLong(),
                acquisitionNode.get("beginViewingDate").asLong(),
                acquisitionNode.get("endViewingDate").asLong(),
                acquisitionNode.get("missionName").asText(),
                quickLookNode != null ? quickLookNode.asText() : null
        );
    }

    /**
     * Retrieves the Feature object with matching id.
     * @param id identifier of the Feature
     * @return the matching Feature object or null if not found.
     */
    public Feature getFeatureById(final String id) {
        if (id == null) {
            return null;
        }

        return this.features.get(id);
    }

    public List<Feature> getFeatures() {
        return this.features.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }
 }
