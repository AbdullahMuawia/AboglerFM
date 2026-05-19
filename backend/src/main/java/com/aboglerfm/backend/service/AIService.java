package com.aboglerfm.backend.service;

import com.aboglerfm.backend.dto.RecommendationDto;
import com.aboglerfm.backend.dto.RecommendationResponse;
import com.aboglerfm.backend.dto.TopArtistDto;
import com.aboglerfm.backend.repository.ListeningEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AIService {

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.model}")
    private String model;

    @Value("${ollama.num-ctx}")
    private int numCtx;

    @Value("${ollama.num-predict}")
    private int numPredict;

    @Value("${ollama.temperature}")
    private double temperature;

    private final ListeningEventRepository listeningEventRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public AIService(ListeningEventRepository listeningEventRepository, RestTemplate restTemplate) {
        this.listeningEventRepository = listeningEventRepository;
        this.restTemplate = restTemplate;
    }

    public RecommendationResponse getRecommendations(String username) throws Exception {
        List<TopArtistDto> topArtists = listeningEventRepository.findTopArtists(username);

        String artistList = topArtists.stream()
                .limit(10)
                .map(TopArtistDto::getArtist)
                .collect(Collectors.joining(", "));
        if (artistList.isBlank()) {
            artistList = "none";
        }

        String prompt = """
You are a music recommendation engine.
Given these top artists: %s

Return ONLY a JSON object with this exact shape:
{"recommendations":[{"song":"...","artist":"...","reason":"..."}]}

Use the key "recommendations" exactly. Do not include any other keys or text.
""".formatted(artistList);

        Map<String, Object> options = new HashMap<>();
        options.put("num_ctx", numCtx);
        options.put("num_predict", numPredict);
        options.put("temperature", temperature);

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("prompt", prompt);
        body.put("stream", false);
        body.put("format", "json");
        body.put("options", options);

        String responseJson = restTemplate.postForObject(
                baseUrl + "/api/generate",
                body,
                String.class
        );

        if (responseJson == null || responseJson.isBlank()) {
            return new RecommendationResponse(List.of());
        }

        JsonNode response = mapper.readTree(responseJson);
        String text = response.path("response").asText();
        String json = extractJson(text);
        try {
            return parseRecommendations(json);
        } catch (Exception e) {
            return new RecommendationResponse(List.of());
        }
    }

    private String extractJson(String text) {
        int start = text.indexOf('{');
        int end = text.lastIndexOf('}');
        if (start < 0 || end < 0 || end <= start) {
            return "{\"recommendations\":[]}";
        }
        return text.substring(start, end + 1);
    }

    private RecommendationResponse parseRecommendations(String json) throws Exception {
        JsonNode root = mapper.readTree(json);

        JsonNode recsNode = root.path("recommendations");
        if (!recsNode.isArray()) {
            if (root.isArray()) {
                recsNode = root;
            } else if (root.fields().hasNext()) {
                recsNode = root.fields().next().getValue();
            }
        }

        List<RecommendationDto> recs = new ArrayList<>();
        if (recsNode != null && recsNode.isArray()) {
            for (JsonNode n : recsNode) {
                String song = n.path("song").asText("");
                String artist = n.path("artist").asText("");
                String reason = n.path("reason").asText("");
                if (!song.isBlank() || !artist.isBlank()) {
                    recs.add(new RecommendationDto(song, artist, reason));
                }
            }
        }

        return new RecommendationResponse(recs);
    }
}