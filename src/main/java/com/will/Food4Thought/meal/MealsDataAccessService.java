package com.will.Food4Thought.meal;


import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

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
       return jdbcTemplate.query(sql, (rs, i) -> {

           return new Meals(rs.getInt("id"),
                   rs.getString("name"),
                   Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                   allergies(rs.getString("allergy_info")),
                   Arrays.asList((rs.getString("ingredients").split(","))),
                   rs.getString("steps"),
                   MealTime.valueOf(rs.getString("meal_time").toUpperCase()),
                   null
           );
       });
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
        return 0;
    }

    @Override
    public int deleteMeals(Integer id) {
        String sql="DELETE FROM meals WHERE id=?";
        int rowsAffected=jdbcTemplate.update(sql,id);
        return rowsAffected;
    }

    @Override
    public int updateMeals(Integer id, Meals update) {
        return 0;
    }

    }




