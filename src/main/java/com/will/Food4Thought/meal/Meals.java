package com.will.Food4Thought.meal;

import com.will.Food4Thought.MealTime;

import java.util.List;
import java.util.Objects;

public class Meals {
    private Integer id;
    private String name;
    private String allergyInfo;
    private List<String> ingredients;
    private List<String> steps;
    private MealTime mealTime;

    public Meals() {
    }

    public Meals(String name, String allergyInfo, List<String> ingredients, List<String> steps, MealTime mealTime) {
        this.id = null;
        this.name = name;
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

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
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
        return "meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allergyInfo='" + allergyInfo + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", mealTime=" + mealTime +
                '}';
    }

    public String toCSVIngredients(){
        String output = "";
        for (String i : ingredients) {
            output+= i + ",";
        }
        return output.substring(0, output.length()-1);
    }

    public String toCSVSteps(){
        String output = "";
        for (String i : steps) {
            output+= i + ",";
        }
        return output.substring(0, output.length()-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meals meal = (Meals) o;
        return Objects.equals(id, meal.id) && Objects.equals(name, meal.name) && Objects.equals(allergyInfo, meal.allergyInfo) && Objects.equals(ingredients, meal.ingredients) && Objects.equals(steps, meal.steps) && mealTime == meal.mealTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, allergyInfo, ingredients, steps, mealTime);
    }
}
