package com.will.Food4Thought.meal;

import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;

import java.util.List;
import java.util.Objects;

public class Meals {
    private Integer id;
    private String name;
    private Difficulty difficulty;
    private String allergyInfo;
    private List<String> ingredients;
    private String steps;
    private MealTime mealTime;

    public Meals() {
    }

    public Meals(String name, Difficulty difficulty, String allergyInfo, List<String> ingredients, String steps, MealTime mealTime) {
        this.id = null;
        this.name = name;
        this.difficulty = difficulty;
        this.allergyInfo = allergyInfo;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealTime = mealTime;
    }

    public Meals(Integer id, String name, Difficulty difficulty, String allergyInfo, List<String> ingredients, String steps, MealTime mealTime) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.allergyInfo = allergyInfo;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealTime = mealTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(String allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public MealTime getMealTime() {
        return mealTime;
    }

    public void setMealTime(MealTime mealTime) {
        this.mealTime = mealTime;
    }

    @Override
    public String toString() {
        return "Meals{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", allergyInfo='" + allergyInfo + '\'' +
                ", ingredients=" + ingredients +
                ", steps='" + steps + '\'' +
                ", mealTime=" + mealTime +
                '}';
    }

    public String toStringCSV(){
        String output = "";
        for (String ingredient : ingredients) {
            output+= ingredient + ",";
        }
        return output.substring(0, output.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meals meals = (Meals) o;
        return Objects.equals(id, meals.id) && Objects.equals(name, meals.name) && difficulty == meals.difficulty && Objects.equals(allergyInfo, meals.allergyInfo) && Objects.equals(ingredients, meals.ingredients) && Objects.equals(steps, meals.steps) && mealTime == meals.mealTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, difficulty, allergyInfo, ingredients, steps, mealTime);
    }
}
