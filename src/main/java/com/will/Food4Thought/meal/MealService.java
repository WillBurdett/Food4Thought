package com.will.Food4Thought.meal;

import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import com.will.Food4Thought.person.Person;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    public Meals selectMealByPerson(Person person) {
        Person request = new Person(person.getMainIngredient(), person.getDifficulty(), person.getWantHelp());
        String personIngredients = request.getMainIngredient();
        String personDifficulty = String.valueOf(request.getDifficulty());
        String personMealtime;
        if (request.getLocalTime().getHour() < 11){
            personMealtime = "BREAKFAST";
        } else {
            personMealtime = "('SNACK') OR LOWER(meal_time) = LOWER('DINNER')";
        }
        String sql = "SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time FROM meals WHERE LOWER(ingredients) LIKE LOWER('%" + personIngredients + "%') AND (LOWER(meal_time) = LOWER" + personMealtime + ") AND LOWER(difficulty) = LOWER('" + personDifficulty + "');";
        Meals meal = mealDAO.selectMealByPerson(sql);
        return meal;
    }
}
