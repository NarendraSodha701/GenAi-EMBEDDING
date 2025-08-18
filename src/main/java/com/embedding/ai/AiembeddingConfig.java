package com.embedding.ai;

import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiembeddingConfig {


    @Value("${langchain4j.embedding-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.embedding-model.endpoint}")
    private String endpoint;

    @Value("${langchain4j.embedding-model.deployment-name}")
    private String deploymentName;

    @Bean
    public AzureOpenAiEmbeddingModel embeddingModel() {
        return AzureOpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .endpoint(endpoint)
                .deploymentName(deploymentName)
                .logRequestsAndResponses(true)
                .build();
    }
}


