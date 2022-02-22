package com.will.Food4Thought.meal;

import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MealsDataAccessServiceTest {

//    private MealsDataAccessService underTest;
//    private JdbcTemplate fakeJdbcTemplate;


    @BeforeEach
    void setUp() {
//        fakeJdbcTemplate = mock(JdbcTemplate.class);
//        underTest = new MealsDataAccessService(fakeJdbcTemplate); //injecting mocked jdbcTemplate into underTest object
    }

    @AfterEach
    void tearDown() {
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
    @Disabled
    void selectMealById() {
//        ReflectionTestUtils.setField(underTest, "jdbcTemplate", fakeJdbcTemplate);
//        String sql = """
//                SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time
//                 FROM meals
//                 WHERE meals.id=?
//                 """;
//        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, List.of(Allergies.DAIRY), List.of("Pasta", "Cheese"), "http://www.test_link.com", MealTime.MAIN, null);
//        when(fakeJdbcTemplate.queryForObject(sql, Meals.class)).thenReturn(pasta);
//
//        assertThat(underTest.selectMealById(1)).isEqualTo(pasta);
    }

    @Test
    @Disabled
    void insertMealCheck() {
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