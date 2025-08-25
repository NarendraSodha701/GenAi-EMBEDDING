package com.embedding.ai;

import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiembeddingConfig {


    @Value("${langchain4j.chat-model.api-key}")
    private String chatapiKey;

    @Value("${langchain4j.azure-open-ai.endpoint}")
    private String chatEndpoint;

    @Value("${langchain4j.azure-open-ai.deployment-name}")
    private String chatDeploymentName;

    @Value("${langchain4j.embedding-model.api-key}")
    private String apiKey;

    @Value("${langchain4j.embedding-model.endpoint}")
    private String endpoint;

    @Value("${langchain4j.embedding-model.deployment-name}")
    private String embeddingDeploymentName;


    @Value("${langchain4j.qdrant.url}")
    private String qdrantUrl;

    @Value("${langchain4j.qdrant.key}")
    private String qdrantKey;

    @Bean
    public AzureOpenAiEmbeddingModel embeddingModel() {
        return AzureOpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .endpoint(endpoint)
                .deploymentName(embeddingDeploymentName)
                .logRequestsAndResponses(true)
                .build();
    }

    @Bean
    public AzureOpenAiChatModel chatModel() {
        return AzureOpenAiChatModel.builder()
                .apiKey(chatapiKey)
                .endpoint(chatEndpoint)
                .deploymentName(chatDeploymentName)
                .build();
    }

    @Bean
    public QdrantEmbeddingStore qdrantEmbeddingStore() {
        return QdrantEmbeddingStore.builder()
                .collectionName("restaurant_menu")
                .host(qdrantUrl)
                .port(6334)
                .apiKey(qdrantKey)
                .useTls(true)
                .build();
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingModel embeddingModel, QdrantEmbeddingStore embeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }
}
