package com.will.Food4Thought.meal;

import com.will.Food4Thought.enums.Allergies;
import com.will.Food4Thought.enums.Difficulty;
import com.will.Food4Thought.enums.MealTime;


import com.will.Food4Thought.chef.Chef;
import com.will.Food4Thought.meal.meal_exceptions.LinkInvalidException;
import com.will.Food4Thought.meal.meal_exceptions.MealNotAddedException;

import com.will.Food4Thought.meal.meal_exceptions.MealNotFoundException;

import com.will.Food4Thought.meal.meal_exceptions.RowNotChangedException;
import com.will.Food4Thought.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJdbcTest
class MealServiceTest {

    private MealService underTest;
    private MealDAO fakeMealDao;

    @BeforeEach
    void setUp() {
        fakeMealDao = mock(MealDAO.class);
        underTest = new MealService(fakeMealDao);
    }

    @Test
    void allMealsShown() {
        //Happy path
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<String> lasagneIngredients = Arrays.asList("Lasagne", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(null,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "www.test_link.com", MealTime.MAIN, null);
        Meals lasagne = new Meals(null,"Lasagne", Difficulty.INTERMEDIATE, allergies, lasagneIngredients, "www.test_link.com", MealTime.MAIN, null);

        List<Meals> test = Arrays.asList(pasta,lasagne);
        when(fakeMealDao.selectAllMeals()).thenReturn(test);

        //WHEN
        List<Meals> actual = underTest.selectAllMeals();

        //THEN
        assertThat(actual).isEqualTo(test);
    }

    @Test
    void correctExceptionThrown() {
        //GIVEN
        given(fakeMealDao.selectAllMeals()).willThrow(EmptyResultDataAccessException.class);

        //THEN
        assertThatThrownBy(() -> {
            // WHEN
            underTest.selectAllMeals();
        }).isInstanceOf(MealNotFoundException.class)
                .hasMessage("Meals not found");

    }

    @Test
    void selectMealByIdReturnsCorrectMeal() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<String> lasagneIngredients = Arrays.asList("Lasagne", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "www.test_link.com", MealTime.MAIN, null);
        Meals lasagne = new Meals(2,"Lasagne", Difficulty.INTERMEDIATE, allergies, lasagneIngredients, "www.test_link.com", MealTime.MAIN, null);

        List<Meals> test = Arrays.asList(pasta,lasagne);
        when(fakeMealDao.selectMealById(1)).thenReturn(pasta);

        //WHEN
        Meals actual = underTest.selectMealById(1);

        //THEN
        assertThat(actual).isEqualTo(pasta);
    }

    @Test
    void mealWithIdDoesNotExist() {
        //GIVEN
        when(fakeMealDao.selectMealById(3)).thenThrow(EmptyResultDataAccessException.class);

        //THEN
        assertThatThrownBy(() -> {
            // WHEN
            underTest.selectMealById(3);
        }).isInstanceOf(MealNotFoundException.class)
                .hasMessage("Meal with id number "+ 3 + " does not exist");
    }

    @Test
    void multipleMealsWithIdExist() {
        //GIVEN
        when(fakeMealDao.selectMealById(3)).thenThrow(IncorrectResultSizeDataAccessException.class);

        //THEN
        assertThatThrownBy(() -> {
            // WHEN
            underTest.selectMealById(3);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("Multiple meal with the same id number "+ 3 + " found");
    }

    @Test
    void checkStepsValid() {
        //GIVEN
        String steps = "https://www.testlink.com";

        //WHEN
        Boolean actual = underTest.isStepsValid(steps);

        //THEN
        assertThat(actual).isTrue();
    }

    @Test
    void checkExceptionThrownWhenIsStepsValidCalled() {
        //GIVEN
        String steps = "www.testlink.com";

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            Boolean actual = underTest.isStepsValid(steps);
        }).hasMessage("Check link again");
    }

    @Test
    void exceptionThrownWhenLinkExists() {
        //GIVEN
        String testSteps = "https://www.testlink.com";
        Meals pasta = new Meals(1, "Pasta", Difficulty.BEGINNER, List.of(Allergies.DAIRY),  List.of("Pasta", "Cheese"), "https://www.testlink.com", MealTime.MAIN, null);
        given(fakeMealDao.selectAllMeals()).willReturn(List.of(pasta));

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            Boolean actual = underTest.isStepsPosted(testSteps);
        }).isInstanceOf(LinkInvalidException.class)
                .hasMessage("Link already posted");
    }

    @Test
    void mealNameAlreadyExists() {
        //GIVEN
        String testName = "Pasta";
        Meals pasta = new Meals(1, "Pasta", Difficulty.BEGINNER, List.of(Allergies.DAIRY),  List.of("Pasta", "Cheese"), "https://www.testlink.com", MealTime.MAIN, null);
        given(fakeMealDao.selectAllMeals()).willReturn(List.of(pasta));

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            Boolean actual = underTest.isNameValid(testName);
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage(" Meal with same name found ");
    }

    @Test
    void insertMealWorksProperly() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        ArgumentCaptor<Meals> captor = ArgumentCaptor.forClass(Meals.class);

        //WHEN
        underTest.insertMeal(pasta);
        verify(fakeMealDao).insertMeal(captor.capture());
        Meals testMeal = captor.getValue();

        //THEN
        assertThat(testMeal).isEqualTo(pasta);
    }

    @Test
    void exceptionThrownWhenInserting() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "www.test_link.com", MealTime.MAIN, null);
        given(fakeMealDao.insertMeal(pasta)).willReturn(5);

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            underTest.insertMeal(pasta);
        }).isInstanceOf(MealNotAddedException.class)
                .hasMessage("Meal with id " + 1 + " was not added");
    }

    @Test
    void mealWasDeletedSuccessfully() {
        //GIVEN
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, List.of(Allergies.DAIRY, Allergies.WHEAT), List.of("Pasta", "Cheese"), "http://www.test_link.com", MealTime.MAIN, null);
        given(fakeMealDao.selectMealById(1)).willReturn(pasta);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        //WHEN
        underTest.deleteMeal(1);
        verify(fakeMealDao).deleteMeals(captor.capture());
        Integer testValue = captor.getValue();

        //THEN
        assertThat(testValue).isEqualTo(1);
    }

    @Test
    void rowNotChangedExceptionShown() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        given(fakeMealDao.deleteMeals(1)).willReturn(2);

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            underTest.deleteMeal(1);
        }).isInstanceOf(RowNotChangedException.class)
                .hasMessage("Meal with id " +  1 + " was not deleted");
    }

    @Test
    void mealNotFoundExceptionThrown() {
        //GIVEN
        given(fakeMealDao.selectMealById(1)).willThrow(EmptyResultDataAccessException.class);

        //THEN
        assertThatThrownBy(() -> {
            // WHEN
            underTest.deleteMeal(1);
        }).isInstanceOf(MealNotFoundException.class)
                .hasMessage("Meal with id number "+ 1 + " does not exist");
    }

    @Test
    void mealUpdatedSuccessfully() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        Meals updatedPasta = new Meals(1,"Pasta", Difficulty.INTERMEDIATE, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);

        given(fakeMealDao.selectMealById(1)).willReturn(pasta);
        ArgumentCaptor<Meals> captor = ArgumentCaptor.forClass(Meals.class);

        //WHEN
        underTest.updateById(1, updatedPasta);
        verify(fakeMealDao).updateMeals(anyInt(), captor.capture());
        Meals testMeal = captor.getValue();

        //THEN
        assertThat(testMeal).isEqualTo(updatedPasta);
    }

    @Test
    void updateMealsRowNotChangedException() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        Meals updatedPasta = new Meals(1,"Pasta", Difficulty.INTERMEDIATE, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);

        given(fakeMealDao.updateMeals(1, updatedPasta)).willReturn(2);

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            underTest.updateById(1, updatedPasta);
        }).isInstanceOf(RowNotChangedException.class)
                .hasMessage("Meal with id " + 1 + " was not updated");
    }

    @Test
    void updateMealsMealNotFoundExceptionThrown() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        Meals updatedPasta = new Meals(1,"Pasta", Difficulty.INTERMEDIATE, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        given(fakeMealDao.updateMeals(1, updatedPasta)).willThrow(EmptyResultDataAccessException.class);

        //THEN
        assertThatThrownBy(() -> {
            // WHEN
            underTest.updateById(1, updatedPasta);
        }).isInstanceOf(MealNotFoundException.class)
                .hasMessage("Meal with id number "+ 1 + " does not exist");
    }

    @Test
    void selectMealByPerson() {
        //Happy Path
        //GIVEN
//        Chef chef1 = new Chef(1, "James Oliver", "jamesoliver@hotmail.com", "London", 300.00);
//        Chef chef2 = new Chef(2, "Gordon Ramsey", "gordon@ramsey.com", "London", 500.00);
//        String sql = "SELECT id, name, allergy_info, difficulty, ingredients, steps, meal_time FROM meals WHERE LOWER(ingredients) LIKE '%" + testPerson.getMainIngredient() + "%' AND (LOWER(meal_time) = LOWER(" + MealTime.SNACK + ")) AND LOWER(difficulty) = LOWER('" + testPerson.getDifficulty() + "')";
        Person testPerson = new Person("kielbasa", Difficulty.BEGINNER, false);
        Meals expected = new Meals(2, "Pasta Salad", Difficulty.BEGINNER, List.of(Allergies.DAIRY),  List.of("Kielbasa", "Noodles"), "https://www.inspiredtaste.net/38019/easy-pasta-salad-recipe/#itr-recipe-38019", MealTime.SNACK, null);
        given(fakeMealDao.selectMealByPerson(anyString(), anyBoolean())).willReturn(expected);

        //WHEN
        Meals actual = underTest.selectMealByPerson(testPerson);

        //THEN
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void correctSQLForMultipleIngredientReturnedAndBreakfastTime() {
        //GIVEN
        Person testPerson = new Person("kielbasa, egg", Difficulty.BEGINNER, false);
        Meals expected = new Meals(2, "Pasta Salad", Difficulty.BEGINNER, List.of(Allergies.DAIRY),  List.of("Kielbasa", "Noodles"), "https://www.inspiredtaste.net/38019/easy-pasta-salad-recipe/#itr-recipe-38019", MealTime.SNACK, null);
        given(fakeMealDao.selectMealByPerson(anyString(), anyBoolean())).willReturn(expected);

        //WHEN
        Meals actual = underTest.selectMealByPerson(testPerson);

        //THEN
        assertThat(actual).isEqualTo(expected);
    }

}