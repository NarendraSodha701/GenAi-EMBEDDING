package com.embedding.ai.hotelmenu;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiHotelMenuService {

    private final EmbeddingModel embeddingModel;
    private final QdrantEmbeddingStore qdrantEmbeddingStore;

    @Autowired
    public AiHotelMenuService(EmbeddingModel embeddingModel, QdrantEmbeddingStore qdrantEmbeddingStore) {
        this.embeddingModel = embeddingModel;
        this.qdrantEmbeddingStore = qdrantEmbeddingStore;
    }

    public void addHotelMenu(AiHotelMenu menu){
        Map<String,Object> metaDataMap = new HashMap<>();
        metaDataMap.put("dishName", menu.dishName());
        metaDataMap.put("category", menu.category());
        metaDataMap.put("description", menu.description());
        metaDataMap.put("price", menu.price());
        metaDataMap.put("tags", String.join(",", menu.tags()));

        Metadata metadata = new Metadata(metaDataMap);

        // Create a comprehensive text representation for better embedding
        String textContent = String.format("%s - %s. Category: %s. Price: $%.2f. Tags: %s",
            menu.dishName(), menu.description(), menu.category(),
            menu.price(), String.join(", ", menu.tags()));

        TextSegment textSegment = new TextSegment(textContent, metadata);
        Embedding embedding = embeddingModel.embed(textSegment).content();
        qdrantEmbeddingStore.add(embedding, textSegment);
    }
}
