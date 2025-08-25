package com.embedding.ai.hotelmenu;

import java.util.List;

public record AiHotelMenu(String dishName, String category, String description, Double price, List<String> tags) {
}
