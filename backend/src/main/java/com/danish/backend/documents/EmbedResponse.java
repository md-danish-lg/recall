package com.danish.backend.documents;

import java.util.UUID;

public class EmbedResponse{
    private boolean success;

    private UUID document_id;
    private Integer chunk_count;

    public EmbedResponse() {
    }

    public EmbedResponse(boolean success, UUID document_id, Integer chunk_count) {
        this.success = success;
        this.document_id = document_id;
        this.chunk_count = chunk_count;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UUID getDocument_id() {
        return document_id;
    }

    public void setDocument_id(UUID document_id) {
        this.document_id = document_id;
    }

    public Integer getChunk_count() {
        return chunk_count;
    }

    public void setChunk_count(Integer chunk_count) {
        this.chunk_count = chunk_count;
    }
}
