package com.will.Food4Thought.meal;

import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MealsDataAccessServiceTest {

    private MealsDataAccessService underTest;
    private JdbcTemplate fakeJdbcTemplate;

    @BeforeEach
    void setUp() {
        fakeJdbcTemplate = mock(JdbcTemplate.class); //mocking the JdbcTemplate object so that we don't need to run the
        // SQL statement on a database
        underTest = new MealsDataAccessService(fakeJdbcTemplate); //injecting mocked jdbcTemplate into underTest object
    }

    @Test
    @Disabled
    void selectAllMeals() {
    }

    @Test
    @Disabled
    void allergies() {
    }

    @Test
    void selectMealById() {
//        ReflectionTestUtils.setField(underTest, "jdbcTemplate", fakeJdbcTemplate);
        //GIVEN
        String sql = """
                SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
                FROM meals 
                WHERE meals.id=1
                """;
        List<String> ingredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals testMeal = new Meals("Pasta", Difficulty.BEGINNER, allergies, ingredients, "www.test_link.com", MealTime.MAIN, null);


        //WHEN
        when(fakeJdbcTemplate.queryForObject(sql, Meals.class)).thenReturn(testMeal);
        Meals actual = underTest.selectMealById(1);

        //THEN
        assertThat(actual).isEqualTo(testMeal);
    }

    @Test
    @Disabled
    void insertMeal() {
    }

    @Test
    @Disabled
    void allergyInfo() {
    }

    @Test
    @Disabled
    void ingredientsInfo() {
    }

    @Test
    @Disabled
    void deleteMeals() {
    }

    @Test
    @Disabled
    void updateMeals() {
    }

    @Test
    @Disabled
    void selectMealByPerson() {
    }
}