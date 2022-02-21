package com.will.Food4Thought.meal;

import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import com.will.Food4Thought.person.Person;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

        try {
            return mealDAO.selectAllMeals();
        }catch(EmptyResultDataAccessException e) {
            throw new MealNotFoundException("Meal not found");
        }
    }

    public Meals selectMealById(Integer id){
        try {
            return mealDAO.selectMealById(id);
        }catch(EmptyResultDataAccessException e) {
            throw new MealNotFoundException("Meal with id number "+ id + " does not exist");
        }catch(IncorrectResultSizeDataAccessException e){
            throw new RuntimeException("Multiple meal with the same id number "+ id + " found");
        }

    }

    //This method is used in the insertMeal method and the updateById method
    public boolean isStepsValid(String steps){

        // todo - some of our websites don't have a 'www.' beginning - completed
        String firstSection = "https://";
        String anotherFirstSection = "http://";
        if((steps.substring(0,8).equals(firstSection) || steps.substring(0,7).equals(anotherFirstSection)) ){
            return true;
        }else {
            throw new LinkInvalidException("Check link again");
        }

    }

    //This method is used in the insertMeal method
    public boolean isStepsPosted(String steps){
        for (Meals selectAllMeal : mealDAO.selectAllMeals()) {
            if (selectAllMeal.getSteps().equals(steps)) {
                throw new LinkInvalidException("Link already posted");
            }
        }
        return true;
    }

    //Not sure if this is necessary
    public boolean isNameValid(String mealName){
        for (Meals meals : mealDAO.selectAllMeals()) {
            if(meals.getName().equals(mealName)){
                throw new IllegalStateException(" Meal with same name found ");
            }
        }
        return true;
    }

    public void insertMeal(Meals meals) {

            if (isStepsValid(meals.getSteps()) && isStepsPosted(meals.getSteps()) && isNameValid(meals.getName())) {
                mealDAO.insertMeal(meals);
            } else if (mealDAO.deleteMeals(meals.getId()) != 1){

                    throw new RowNotChangedException("Meal with id " + meals.getId() + " was not added");
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
            if(mealDAO.selectMealById(mealId)!=null && isStepsValid(update.getSteps())) {
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
            personMealtime = "'BREAKFAST'";
        } else {
            personMealtime = "'SNACK') OR LOWER(meal_time) = LOWER('MAIN')";
        }
        String sql = "SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time FROM meals WHERE LOWER(ingredients) LIKE '%" + personIngredients + "%' AND LOWER(meal_time) = LOWER(" + personMealtime + ") AND LOWER(difficulty) = LOWER('" + personDifficulty + "')";
        Meals meal = mealDAO.selectMealByPerson(sql, personWantsHelp);
        return meal;
    }
}
