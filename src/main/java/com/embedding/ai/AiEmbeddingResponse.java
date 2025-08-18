package com.embedding.ai;

public class AiEmbeddingResponse {
    private float[] embeddings;

    public AiEmbeddingResponse(float[] embeddings) {
        this.embeddings = embeddings;
    }

    public float[] getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(float[] embeddings) {
        this.embeddings = embeddings;
    }

}
