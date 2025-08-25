package com.embedding.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiHotelMenuAssistant {

    @SystemMessage("You are a Hotel Menu assistant which help customer provide hotel menu according user query and based on data searched from vector db")
    AiHotelResponse getHotelMenu(String query);

}
