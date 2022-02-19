package com.will.Food4Thought.meal;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    MealDAO mealDAO;

    public MealService(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }

    public List<Meals> selectAllMeals() {

        return mealDAO.selectAllMeals();
    }


    public Meals selectMealById(Integer id){
        return mealDAO.selectMealById(id);

    }

    public void insertMeal(Meals meals) {
        mealDAO.insertMeal(meals);

    }



    public void deleteMeal(Integer id) {

        mealDAO.deleteMeals(id);
    }

    public void updateById(Integer mealId, Meals update) {
        mealDAO.updateMeals(mealId,update);
    }
}
