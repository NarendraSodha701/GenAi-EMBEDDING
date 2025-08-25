package com.embedding.ai.hotelmenu;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiRagService {

    private final ContentRetriever contentRetriever;

    @Autowired
    public AiRagService(ContentRetriever contentRetriever) {
        this.contentRetriever = contentRetriever;
    }

    @Tool("this method will retrieve restaurant menu items from qdrant vector store based on the query")
    public List<Content> searchFromHotelMenu(String query){
        Query qdrantQuery = Query.from(query);
        return contentRetriever.retrieve(qdrantQuery);
    }
}
