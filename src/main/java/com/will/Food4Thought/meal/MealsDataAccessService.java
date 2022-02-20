package com.will.Food4Thought.meal;


import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Repository
public class MealsDataAccessService implements MealDAO{

    private JdbcTemplate jdbcTemplate;

    public MealsDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Meals> selectAllMeals() {

        var sql = """
                SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
                FROM meals;
                """;

        return jdbcTemplate.query(sql, new RowMapper());
    }

    public static List<Allergies> allergies(String input){
        List <Allergies> allergies = new ArrayList<>();
        if (input == null){
            return null;
        }
        for (String s : input.split(",")) {
            Allergies allergy = Allergies.valueOf(s.toUpperCase().replaceAll(" ", ""));
            allergies.add(allergy);
        }
        return allergies;
    }

    @Override
    public Meals selectMealById(Integer id) {
        String sql = """
               SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
                FROM meals 
                WHERE meals.id=?
                """;
        return jdbcTemplate.queryForObject(sql, new RowMapper(),id);
    }

    @Override
    public int insertMeal(Meals meal) {
        String sql = """
                INSERT INTO meals (name, allergy_info, difficulty, ingredients, steps, meal_time)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        int rowsAffected = jdbcTemplate.update(
                sql,
                meal.getName(),
                allergyInfo(meal.getAllergyInfo()),
                String.valueOf(meal.getDifficulty().toString()),
                ingredientsInfo(meal.getIngredients()),
                meal.getSteps(),
                String.valueOf(meal.getMealTime().toString())
        );
        return rowsAffected;
    }
    public static String allergyInfo (List <Allergies> allergies){
        if (allergies == null){
            return null;
        }
        String output = "";
        for (Allergies allergy : allergies) {
            output+= allergy + ",";
        }
        return output.substring(0, output.length() - 1);
    }
    public static String ingredientsInfo (List <String> ingredients){
        if (ingredients == null){

            return null;
        }
        String output = "";
        for ( String ingredient : ingredients) {
            output+= ingredient + ",";
        }
        return output.substring(0, output.length() - 1);
    }



    @Override
    public int deleteMeals(Integer id) {
        String sql="DELETE FROM meals WHERE id=?";
        int rowsAffected=jdbcTemplate.update(sql,id);
        return rowsAffected;
    }


    @Override
    public int updateMeals(Integer id, Meals update) {
        String sql= "UPDATE meals SET name = ?, allergy_info=?, difficulty =?, ingredients =?, steps =?, meal_time=? WHERE id =?";

        int rowAffected = jdbcTemplate.update(sql,update.getName(),
                allergyInfo(update.getAllergyInfo()),
                String.valueOf(update.getDifficulty().toString()),
                ingredientsInfo(update.getIngredients()),
                update.getSteps(),
                String.valueOf(update.getMealTime().toString()),id);

        if(rowAffected ==1){
            return 1;
        }
        return 0;
    }
    @Override
    public Meals selectMealByPerson(String sql){
        List<Meals> meals = jdbcTemplate.query(sql, new RowMapper());
        if (meals == null){
            throw new MealNotFoundException("Sorry! A meal could not be found meeting your criteria. Please try again with different criteria.");
        }
        if (meals.size() == 0){
            throw new MealNotFoundException("Sorry! A meal could not be found meeting your criteria. Please try again with different criteria.");
        }
        Random random = new Random();
        int index = random.nextInt(meals.size());
        return meals.get(index);
    }
}
