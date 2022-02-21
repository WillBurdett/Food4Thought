package com.will.Food4Thought;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MealTime {
    BREAKFAST ("BREAKFAST"),
    MAIN("MAIN"),
    SNACK("SNACK");

    private String mealTime;

    MealTime(String mealTime){
        this.mealTime = mealTime;
    }

    @JsonCreator
    public static MealTime fromString(String mealTime) {
        return mealTime == null
                ? null
                : MealTime.valueOf(mealTime.toUpperCase());
    }

    @JsonValue
    public String getMealTime() {
        return this.mealTime.toUpperCase();
    }

}
