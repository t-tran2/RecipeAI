package com.example.recipeai.GPT3Api;

public class GPT3Request {
    private String prompt;
    private int maxTokens;

    public GPT3Request(String prompt, int maxTokens) {
        this.prompt = prompt;
        this.maxTokens = maxTokens;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }
}