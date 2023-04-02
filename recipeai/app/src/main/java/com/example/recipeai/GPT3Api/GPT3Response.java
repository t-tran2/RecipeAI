package com.example.recipeai.GPT3Api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GPT3Response {

    @SerializedName("choices")
    private List<Choice> choices;

    public String getGeneratedText() {
        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).text;
        }
        return null;
    }

    private static class Choice {
        @SerializedName("text")
        private String text;

        @SerializedName("index")
        private int index;

        @SerializedName("logprobs")
        private Object logprobs;

        @SerializedName("finish_reason")
        private String finishReason;

        @SerializedName("prompt")
        private String prompt;
    }
}
