package com.will.Food4Thought.meal;

import com.will.Food4Thought.enums.Allergies;
import com.will.Food4Thought.enums.Difficulty;
import com.will.Food4Thought.enums.MealTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class MealsDataAccessServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private MealsDataAccessService underTest;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {

        jdbcTemplate = new JdbcTemplate();
        underTest = new MealsDataAccessService(jdbcTemplate); //injecting jdbcTemplate instance into underTest instance
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2) //creating the dataSource instance which is of EmbeddedDBType H2
                .addScript("classpath:jdbc/meals-schema.sql") //builds the .sql file which has the logic to create in-memory test table 'meals'
                .addScript("classpath:jdbc/meals-test-data.sql") //builds the .sql file which has the logic to insert data into in-memory test table 'meals'
                .build();
        underTest.setDataSource(dataSource); //injecting the dataSource into underTest using its .setDataSource setter method

    }

    @Test
    void selectAllMeals() {

        List<Meals> actual = underTest.selectAllMeals();
        Meals meal1 = new Meals(1, "Test Meal 1", Difficulty.INTERMEDIATE, null, List.of("Chicken"), "https://www.testlink1.com", MealTime.MAIN, null);
        Meals meal2 = new Meals(2, "Test Meal 2", Difficulty.BEGINNER, List.of(Allergies.DAIRY), List.of("Kielbasa","Noodles"), "https://www.testlink2.com", MealTime.SNACK, null);
        Meals meal3 = new Meals(3, "Test Meal 3", Difficulty.BEGINNER, List.of(Allergies.DAIRY), List.of("Sausage","Noodles"), "https://www.testlink3.com", MealTime.MAIN, null);
        Meals meal4 = new Meals(4, "Test Meal 4", Difficulty.INTERMEDIATE, List.of(Allergies.SOY,Allergies.WHEAT), List.of("Milk","Eggs","Corn Meal"), "https://www.testlink4.com", MealTime.BREAKFAST, null);
        List<Meals> expected = List.of(meal1,meal2,meal3,meal4);

        assertEquals(actual,expected);

    }

    @Test
    void selectMealById() {

        Meals expected = new Meals(1, "Test Meal 1", Difficulty.INTERMEDIATE, null, List.of("Chicken"), "https://www.testlink1.com", MealTime.MAIN, null);
        Meals actual = underTest.selectMealById(1);

        assertEquals(expected, actual);
    }

    @Test
    void insertMeal() {

        Meals testMeal = new Meals(5,"Test Meal 5", Difficulty.BEGINNER, List.of(Allergies.DAIRY, Allergies.WHEAT), List.of("Pasta", "Cheese"), "http://www.testlink5.com", MealTime.MAIN, null);

        Integer actual = underTest.insertMeal(testMeal);

        assertEquals(1, actual);

    }

    @Test
    void deleteMeals() {
        Integer actual = underTest.deleteMeals(4);

        assertEquals(1, actual);
    }

    @Test
    void updateMeals() {

        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, List.of(Allergies.DAIRY, Allergies.WHEAT), List.of("Pasta", "Cheese"), "http://www.test_link.com", MealTime.MAIN, null);

        Integer actual = underTest.updateMeals(1, pasta);

        assertEquals(1, actual);
    }

    @Test
    void selectMealByPerson_ChefsHelpNotNeeded() {
        String sql = "SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time FROM meals WHERE LOWER(ingredients) LIKE '%sausage%'";
        Meals actual = underTest.selectMealByPerson(sql, false);
        Meals expected = new Meals(3, "Test Meal 3", Difficulty.BEGINNER, List.of(Allergies.DAIRY), List.of("Sausage","Noodles"), "https://www.testlink3.com", MealTime.MAIN, null);

        assertEquals(expected, actual);
    }
}