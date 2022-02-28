package com.will.Food4Thought.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Allergies {
    DAIRY ("DAIRY"),
    NUTS ("NUTS"),
    SHELLFISH("SHELLFISH"),
    SOY("SOY"),
    WHEAT("WHEAT"),
    SESAME("SESAME");


    private String allergies;

    Allergies(String allergies){
        this.allergies = allergies;
    }


    @JsonCreator

    public static Allergies fromString(String allergy) {
        return allergy == null
                ? null
                : Allergies.valueOf(allergy.toUpperCase());
    }

    @JsonValue
    public String getAllergies() {
        return this.allergies.toUpperCase();
    }
}