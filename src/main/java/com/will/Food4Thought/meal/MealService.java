package com.will.Food4Thought.meal;

import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import com.will.Food4Thought.person.Person;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static com.will.Food4Thought.Difficulty.*;

@Service
public class MealService {

    MealDAO mealDAO;

    public MealService(MealDAO mealDAO) {
        this.mealDAO = mealDAO;
    }

    public List<Meals> selectAllMeals() {
        //not sure if this bit is needed
        if(mealDAO.selectAllMeals()==null){
            throw new MealNotFoundException("No meals available");
        }
        return mealDAO.selectAllMeals();
    }

    public Meals selectMealById(Integer id){
        try {
            return mealDAO.selectMealById(id);
        }catch(EmptyResultDataAccessException e) {
            throw new MealNotFoundException("Meal with id number "+ id + " does not exist");
        }

    }

    //This method is used in the insertMeal method
    public boolean checkIfStepsIsValid(String steps){
        for (Meals selectAllMeal : mealDAO.selectAllMeals()) {
            if (selectAllMeal.getSteps().equals(steps)) {
                throw new LinkInvalidException("Link already posted");
            }
        }

        // todo - some of our websites don't have a 'www.' beginning
        String firstSection = "https://www.";
        String anotherFirstSection = "http://www.";
        if((steps.substring(0,12).equals(firstSection) || steps.substring(0,11).equals(anotherFirstSection)) ){
            return true;
        }else {
            throw new LinkInvalidException("Check link again");
        }

    }
    public void insertMeal(Meals meals) {
        if(checkIfStepsIsValid(meals.getSteps())){
            mealDAO.insertMeal(meals);
        }else{
            if(mealDAO.deleteMeals(meals.getId())!=1){
                throw new RowNotChangedException("Meal with id " + meals.getId() + " was not added");
            }
        }
    }

    public void deleteMeal(Integer id) {
        try {
            if(mealDAO.selectMealById(id)!=null) {
                mealDAO.deleteMeals(id);
            }else if(mealDAO.deleteMeals(id)!=1){
                    throw new RowNotChangedException("Meal with id " +  id + " was not deleted");
                }

    }catch(EmptyResultDataAccessException e) {
            throw new MealNotFoundException("Meal with id number "+ id + " does not exist");
            //This catches the EmptyResultDataAccessException thrown by JDBC template
        }

    }

    public void updateById(Integer mealId, Meals update) {
        try{
            if(mealDAO.selectMealById(mealId)!=null && checkIfStepsIsValid(update.getSteps())) {
                mealDAO.updateMeals(mealId,update);
            } else if(mealDAO.updateMeals(mealId,update)!=1){
                throw new RowNotChangedException("Meal with id " + mealId + " was not updated");
            }
        }catch(EmptyResultDataAccessException e) {
            throw new MealNotFoundException("Meal with id number "+ mealId + " does not exist");
            //This catches the EmptyResultDataAccessException thrown by the JDBC template
        }
    }

    public Meals selectMealByPerson(Person person) {
        // creating new person to return LocalTime.now()
        Person request = new Person(person.getMainIngredient(), person.getDifficulty(), person.getWantHelp());

        // person values saved to placeholders to make sql string concatenation easier
        String personIngredients = request.getMainIngredient().toLowerCase();
        String personDifficulty = String.valueOf(request.getDifficulty());
        Boolean personWantsHelp = request.getWantHelp();

        // determining thr meal_time listed based on the time
        String personMealtime;
        if (request.getLocalTime().getHour() < 11){
            personMealtime = "BREAKFAST";
        } else {
            personMealtime = "('SNACK') OR LOWER(meal_time) = LOWER('MAIN')";
        }
        String sql = "SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time FROM meals WHERE (LOWER(ingredients) LIKE '%" + personIngredients + "%') AND (LOWER(meal_time) = LOWER" + personMealtime + ") AND LOWER(difficulty) = LOWER('" + personDifficulty + "');";
        Meals meal = mealDAO.selectMealByPerson(sql, personWantsHelp);
        return meal;
    }
}
