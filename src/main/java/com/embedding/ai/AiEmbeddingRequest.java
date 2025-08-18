package com.embedding.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AiEmbeddingRequest {
    @JsonProperty("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
