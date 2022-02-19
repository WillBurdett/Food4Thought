package com.will.Food4Thought.meal;

import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static com.will.Food4Thought.meal.MealsDataAccessService.allergies;

public class RowMapper implements org.springframework.jdbc.core.RowMapper<Meals> {
    @Override
    public Meals mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Meals(rs.getInt("id"),
                rs.getString("name"),
                Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                allergies(rs.getString("allergy_info")),
                Arrays.asList((rs.getString("ingredients").split(","))),
                rs.getString("steps"),
                MealTime.valueOf(rs.getString("meal_time").toUpperCase()),
                null
        );


    }

}
