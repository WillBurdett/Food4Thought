package com.will.Food4Thought.person;

import com.will.Food4Thought.Difficulty;

import java.time.LocalTime;
import java.util.Objects;

public class Person {
    private String mainIngredient;
    private Difficulty difficulty;
    private LocalTime localTime;
    private Boolean wantHelp;

    public Person() {
    }

    public Person(String mainIngredient, Difficulty difficulty, Boolean wantHelp) {
        this.mainIngredient = mainIngredient;
        this.difficulty = difficulty;
        this.localTime = LocalTime.now();
        this.wantHelp = wantHelp;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public void setMainIngredient(String mainIngredient) {
        this.mainIngredient = mainIngredient;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Boolean getWantHelp() {
        return wantHelp;
    }

    public void setWantHelp(Boolean wantHelp) {
        this.wantHelp = wantHelp;
    }

    @Override
    public String toString() {
        return "Person{" +
                "mainIngredient='" + mainIngredient + '\'' +
                ", difficulty=" + difficulty +
                ", localTime=" + localTime +
                ", wantHelp=" + wantHelp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(mainIngredient, person.mainIngredient) && difficulty == person.difficulty && Objects.equals(localTime, person.localTime) && Objects.equals(wantHelp, person.wantHelp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainIngredient, difficulty, localTime, wantHelp);
    }
}
