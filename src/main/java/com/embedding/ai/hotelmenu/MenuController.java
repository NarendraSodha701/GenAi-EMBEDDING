package com.embedding.ai.hotelmenu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final AiHotelMenuService menuService;
    private final AiHotelMenuAssistant menuAssistant;

    @Autowired
    public MenuController(AiHotelMenuService menuService, AiHotelMenuAssistant menuAssistant) {
        this.menuService = menuService;
        this.menuAssistant = menuAssistant;
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addMenuItem(@RequestBody AiHotelMenu menuItem) {
        try {
            menuService.addHotelMenu(menuItem);
            return ResponseEntity.ok(Map.of("message", "Menu item added successfully!"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Failed to add menu item: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<AiHotelMenu>> searchMenu(@RequestParam String query) {
        try {
            List<AiHotelMenu> results = menuAssistant.getHotelMenuRecommendations(query).menuList();
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<Map<String, String>> chatWithMenuAssistant(@RequestParam String query) {
        try {
            String response = String.valueOf(menuAssistant.getHotelMenuRecommendations(query));
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("response", "Sorry, I'm unable to process your request at the moment."));
        }
    }
}
