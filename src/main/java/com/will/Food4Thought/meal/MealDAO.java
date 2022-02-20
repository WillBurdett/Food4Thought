package com.will.Food4Thought.meal;

import java.util.List;

public interface MealDAO {

    List<Meals> selectAllMeals();
    Meals selectMealById(Integer id);
    int insertMeal(Meals meal);
    int deleteMeals(Integer id);
    int updateMeals(Integer id, Meals update);
    Meals selectMealByPerson(String sql, Boolean wantHelp);
}
