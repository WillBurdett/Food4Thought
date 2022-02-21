package com.will.Food4Thought.dao;


import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import com.will.Food4Thought.meal.Meals;
import com.will.Food4Thought.meal.MealsDataAccessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJdbcTest
public class MealsDAOTest {

    private JdbcTemplate jdbcTemplate;
    private MealsDataAccessService underTest;

    @Autowired
    public MealsDAOTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        underTest = new MealsDataAccessService(jdbcTemplate);
    }

    @Test
    void selectAllMeals_shouldReturnListOfAllMeals(){
        // Given
        // our DB has 68 rows

        // When
        List<Meals> meals = underTest.selectAllMeals();
        int actual = meals.size();
        int expected = 68;

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void selectMealById_shouldReturnMealWithMatchingId(){
        // Given
        // our Meal with id 1 is Jamaican Jerk Chicken

        // When
        Meals actual = underTest.selectMealById(1);
        Meals expected = new Meals(1, "Jamaican Jerk Chicken", Difficulty.INTERMEDIATE, null,  List.of("Chicken"), "https://www.foodandwine.com/recipes/jamaican-jerk-chicken", MealTime.MAIN, null);

        // Then
        assertEquals(expected, actual);

    }
}
