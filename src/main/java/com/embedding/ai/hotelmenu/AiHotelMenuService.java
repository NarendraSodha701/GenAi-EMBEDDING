package com.embedding.ai;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AiHotelMenuService {

    private final EmbeddingModel embeddingModel;

    private final QdrantEmbeddingStore qdrantEmbeddingStore;

    public void addHotelMenu(HotelMenu menu){
        Map<String,Object> metaDataMap = new HashMap<>();
        metaDataMap.put("dishName",menu.dishName());
        metaDataMap.put("category",menu.category());;
        metaDataMap.put("description",menu.description());
        metaDataMap.put("price",menu.price());
        metaDataMap.put("tags", String.join(",", menu.tags()));
        Metadata metadata = new Metadata(metaDataMap);
        TextSegment textSegment = new TextSegment(menu.dishName(),metadata);
        Embedding embedding = embeddingModel.embed(textSegment).content();
        qdrantEmbeddingStore.add(embedding,textSegment);

    }


}
