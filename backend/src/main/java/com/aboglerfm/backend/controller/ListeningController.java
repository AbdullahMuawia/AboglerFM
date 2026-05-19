package com.aboglerfm.backend.controller;

import com.aboglerfm.backend.dto.TrackDto;
import com.aboglerfm.backend.service.ListeningService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class ListeningController {

    private final ListeningService listeningService;

    public ListeningController(ListeningService listeningService) {
        this.listeningService = listeningService;
    }

    @PostMapping("/sync")
    public Map<String, Object> sync(Authentication auth) {
        return listeningService.syncUser(auth.getName());
    }

    @GetMapping("/history")
    public List<TrackDto> history(Authentication auth) {
        return listeningService.getHistory(auth.getName());
    }
}