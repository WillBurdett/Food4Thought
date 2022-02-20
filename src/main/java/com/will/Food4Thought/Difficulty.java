package com.will.Food4Thought;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Difficulty {
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED");

    private String difficulty;

    Difficulty(String difficulty){
        this.difficulty = difficulty;
    }

    @JsonCreator
    public static Difficulty fromString(String difficulty) {
        return difficulty == null
                ? null
                : Difficulty.valueOf(difficulty.toUpperCase());
    }

    @JsonValue
    public String getDifficulty() {
        return this.difficulty.toUpperCase();
    }
}
