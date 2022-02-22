package com.will.Food4Thought.meal;


import com.will.Food4Thought.chef.Chef;
import com.will.Food4Thought.chef.ChefMapper;
import com.will.Food4Thought.meal.meal_exceptions.MealNotFoundException;
import com.will.Food4Thought.meal.utils.Utilities;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class MealsDataAccessService implements MealDAO {

    private JdbcTemplate jdbcTemplate;
    public MealsDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Utilities utilities = new Utilities();

    @Override
    public List<Meals> selectAllMeals() {

        var sql = """
                SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
                FROM meals;
                """;

        return jdbcTemplate.query(sql, new MealMapper());
    }

    @Override
    public Meals selectMealById(Integer id) {

        String sql = """
                SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
                 FROM meals 
                 WHERE meals.id=?
                 """;
        return jdbcTemplate.queryForObject(sql, new MealMapper(), id);
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
                utilities.allergyInfo(meal.getAllergyInfo()),
                String.valueOf(meal.getDifficulty().toString()),
                utilities.ingredientsInfo(meal.getIngredients()),
                meal.getSteps(),
                String.valueOf(meal.getMealTime().toString())
        );
        return rowsAffected;
    }

    @Override
    public int deleteMeals(Integer id) {
            String sql = "DELETE FROM meals WHERE id=?";
            int rowsAffected = jdbcTemplate.update(sql, id);
            return rowsAffected;
    }

    @Override
    public int updateMeals(Integer id, Meals update) {
        String sql= "UPDATE meals SET name = ?, allergy_info=?, difficulty =?, ingredients =?, steps =?, meal_time=? WHERE id =?";

        int rowAffected = jdbcTemplate.update(sql,update.getName(),
                utilities.allergyInfo(update.getAllergyInfo()),
                String.valueOf(update.getDifficulty().toString()),
                utilities.ingredientsInfo(update.getIngredients()),
                update.getSteps(),
                String.valueOf(update.getMealTime().toString()),id);

        if(rowAffected == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public Meals selectMealByPerson(String sql, Boolean wantHelp){
        List<Meals> meals = jdbcTemplate.query(sql, new MealMapper());
        if (meals == null){
            throw new MealNotFoundException("Sorry! A meal could not be found meeting your criteria. Please try again with different criteria.");
        }
        if (meals.size() == 0){
            throw new MealNotFoundException("Sorry! A meal could not be found meeting your criteria. Please try again with different criteria.");
        }
        // Pick a random meal from the short-list meeting the criteria
        Random random = new Random();
        int index = random.nextInt(meals.size());
        Meals selection = meals.get(index);

        // if they don't want help, no need to attach a list of chefs
        if (!wantHelp){
            return selection;
        }

        // select query matching the id of selection to a chef
        String chefSQL = "SELECT chefs.id, chefs.name, chefs.location,chefs.price,chefs.email FROM chefs INNER JOIN matches ON matches.chefs_id = chefs.id INNER JOIN meals ON meals.id = matches.meals_id WHERE meals.id = " + selection.getId() + ";";
        List<Chef> chefs = jdbcTemplate.query(chefSQL,new ChefMapper());
        selection.setChefs(chefs);
        return selection;
    }
}

