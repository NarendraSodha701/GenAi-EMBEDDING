package com.embedding.ai;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;


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

    private static final Collections.Distance distance = Collections.Distance.Cosine;


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
                .collectionName("ai_hotel_menu")
                .host(qdrantUrl)
                .port(6334)
                .apiKey(qdrantKey)
                .useTls(false)
                .build();
    }

    @Bean
    public QdrantClient qdrantConfig() throws ExecutionException, InterruptedException {
        QdrantClient client = new QdrantClient(
                QdrantGrpcClient.newBuilder(qdrantUrl, 6334, false).withApiKey(qdrantKey)
                        .build());
        if (!client.collectionExistsAsync("ai_hotel_menu").get()) {
            Collections.CollectionOperationResponse response = client.createCollectionAsync(
                    "ai_hotel_menu",
                    Collections.VectorParams.newBuilder().setDistance(distance).setSize(768).build()).get();
        }
        return client;
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingModel embeddingModel) {
        EmbeddingStore<TextSegment> embeddingStore =
                QdrantEmbeddingStore.builder()
                        .collectionName("ai_hotel_menu")
                        .host(qdrantUrl)
                        .port(6334)
                        .apiKey(qdrantKey)
                        .useTls(false)
                        .build();
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }
}
