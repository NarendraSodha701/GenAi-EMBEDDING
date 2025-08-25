package com.embedding.ai.hotelmenu;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiHotelMenuAssistant {

    @SystemMessage("You are a Restaurant Menu assistant that helps customers find dishes based on their preferences. " +
                  "Use the provided menu data from the vector database to recommend dishes. " +
                  "Be conversational and helpful. If asked about dietary restrictions, price ranges, or specific cuisines, " +
                  "focus on those aspects. Always provide dish names, descriptions, and prices when available.")
    AiHotelResponse getHotelMenuRecommendations(String query);

}
