package com.aboglerfm.backend.service;

import com.aboglerfm.backend.dto.TrackDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LastFmService {

    @Value("${lastfm.api.key}")
    private String apiKey;

    @Value("${lastfm.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public LastFmService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TrackDto> getRecentTracks(String username) {
        String url = baseUrl + "?method=user.getrecenttracks" +
                "&user=" + username +
                "&api_key=" + apiKey +
                "&format=json" +
                "&limit=50";

        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        JsonNode tracks = response.path("recenttracks").path("track");

        List<TrackDto> result = new ArrayList<>();

        for (JsonNode track : tracks) {
            TrackDto dto = new TrackDto();

            dto.setName(track.path("name").asText());
            dto.setArtist(track.path("artist").path("#text").asText());
            dto.setAlbum(track.path("album").path("#text").asText());

            JsonNode images = track.path("image");
            if (images.isArray() && images.size() >= 3) {
                dto.setImageUrl(images.get(2).path("#text").asText());
            }

            JsonNode attr = track.path("@attr");
            dto.setNowPlaying(!attr.isMissingNode() && attr.path("nowplaying").asBoolean());

            if (!dto.isNowPlaying()) {
                JsonNode dateNode = track.path("date");
                dto.setPlayedAt(dateNode.path("#text").asText());
                String uts = dateNode.path("uts").asText();
                if (uts != null && !uts.isBlank()) {
                    dto.setPlayedAtEpoch(Long.parseLong(uts));
                }
            }

            result.add(dto);
        }

        return result;
    }
}