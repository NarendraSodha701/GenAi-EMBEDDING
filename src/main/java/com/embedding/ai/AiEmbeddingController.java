package com.embedding.ai;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AiEmbeddingController {
    private final AzureOpenAiEmbeddingModel embeddingModel;

    public AiEmbeddingController(AzureOpenAiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @PostMapping("/generate-embedding")
    public ResponseEntity<?> generateEmbedding(@RequestBody AiEmbeddingRequest request) {
        try {
            Response<Embedding> response = embeddingModel.embed(request.getText());
            return ResponseEntity.ok(new AiEmbeddingResponse(response.content().vector()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal error: " + e.getMessage());
        }
    }

}