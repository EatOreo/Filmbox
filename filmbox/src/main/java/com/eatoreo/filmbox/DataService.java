package com.eatoreo.filmbox;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataService {

    @Value("classpath:data/films.json")
    private Resource filmsResourceFile;

    public List<String> getFilms() {
        try {
            var jsonFile = filmsResourceFile.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            List<String> keys = new ArrayList<>();
            rootNode.fieldNames().forEachRemaining(e -> keys.add(e));
            return keys;
        } catch (Exception e) {
            return null;
        }
    }
}
