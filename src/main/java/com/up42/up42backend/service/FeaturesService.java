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
import java.util.stream.Collectors;

@Service
public class FeaturesService {
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
            e.printStackTrace();
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

    public Feature getFeatureById(final String id) {
        return this.features.get(id);
    }

    public List<Feature> getFeatures() {
        return this.features.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }
 }
