package com.will.Food4Thought.meal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MealsDataAccessServiceTest {

//    private MealsDataAccessService underTest;
//    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
//        underTest = new MealsDataAccessService(jdbcTemplate); //injecting mocked jdbcTemplate into underTest object
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
        //GIVEN
//        List<String> ingredients = Arrays.asList("Pasta", "Cheese");
//        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
//        Meals testMeal = new Meals(null,"Pasta", Difficulty.BEGINNER, allergies, ingredients, "www.test_link.com", MealTime.MAIN, null);
//        underTest.insertMeal(testMeal);

        //WHEN
//        Meals actual = underTest.selectMealById(1);

        //THEN
//        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(testMeal);
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