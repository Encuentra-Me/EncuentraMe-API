package com.research_and_mobile_solutions.encuentra_me.dto;

public class SimilarityResponse {
    private String externalId;
    private Float similarity;

    public SimilarityResponse(String externalId, Float similarity) {
        this.externalId = externalId;
        this.similarity = similarity;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Float similarity) {
        this.similarity = similarity;
    }
}