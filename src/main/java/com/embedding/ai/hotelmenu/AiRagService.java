package com.embedding.ai;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRagService {

    private final ContentRetriever contentRetriever;

    public AiRagService(EmbeddingModel embeddingModel, ContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    @Tool("this method will retrieve hotel menu from qdrant vector store apply if require into query")
    public List<Content> searchFromHotelMenu(String query){
        Query qdrantQuery = Query.from(query);
        return contentRetriever.retrieve(qdrantQuery);
    }

}
