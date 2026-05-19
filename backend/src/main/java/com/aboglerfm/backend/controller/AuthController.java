package com.aboglerfm.backend.controller;

import com.aboglerfm.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.aboglerfm.backend.model.User;
import com.aboglerfm.backend.repository.UserRepository;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Value("${lastfm.api.key}")
    private String apiKey;

    @Value("${lastfm.api.secret}")
    private String apiSecret;

    @Value("${lastfm.api.base-url}")
    private String baseUrl;

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }


    @GetMapping("/lastfm/login-url")
    public Map<String, String> getLoginUrl() {
        String callback = URLEncoder.encode("http://localhost:5173/auth/lastfm/callback", StandardCharsets.UTF_8);
        String url = "https://www.last.fm/api/auth/?api_key=" + apiKey
            + "&cb=" + callback;
        return Map.of("url", url);


    }

    @GetMapping("/lastfm/callback")
    public Map<String, String> handleCallback(@RequestParam String token) throws Exception {

        String sig = md5("api_key" + apiKey + "methodauth.getSession" + "token" + token + apiSecret);

        String url = baseUrl + "/?method=auth.getSession"
                + "&token=" + token
                + "&api_key=" + apiKey
                + "&api_sig=" + sig
                + "&format=json";

        JsonNode response = restTemplate.getForObject(url, JsonNode.class);
        String username = response.path("session").path("name").asText();
        String sessionKey = response.path("session").path("key").asText();

        User user = userRepository.findByUsername(username)
            .orElseGet(() -> {
                User u = new User();
                u.setUsername(username);
                return u;
            });

        user.setSessionKey(sessionKey);
        userRepository.save(user);

        String jwt = jwtUtil.generateToken(username);
        return Map.of("token", jwt, "username", username);
    }

    private String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }

}
