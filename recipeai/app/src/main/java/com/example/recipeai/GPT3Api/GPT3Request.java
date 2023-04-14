package com.example.recipeai.GPT3Api;

public class GPT3Request {
    private String prompt;
    private int max_tokens;
    private double temperature;
    private String model;

    public GPT3Request(String prompt, int max_tokens, double temperature, String model) {
        this.prompt = prompt;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getmax_tokens() {
        return max_tokens;
    }

    public void setmax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;

    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
