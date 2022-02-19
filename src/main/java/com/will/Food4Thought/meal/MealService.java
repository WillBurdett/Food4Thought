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


    public void deleteMeal(Integer id) {

        mealDAO.deleteMeals(id);
    }
}
