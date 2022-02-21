package com.will.Food4Thought.meal;

import com.will.Food4Thought.Allergies;
import com.will.Food4Thought.Difficulty;
import com.will.Food4Thought.MealTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.relational.core.sql.In;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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



//    @Test
//    void insertMealWorksProperly() {
//        //GIVEN
//        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
//        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
//        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
//        ArgumentCaptor<Meals> captor = ArgumentCaptor.forClass(Meals.class);
//
//        //WHEN
//        underTest.insertMeal(pasta);
//        verify(fakeMealDao).insertMeal(captor.capture());
//        Meals testMeal = captor.getValue();
//
//        //THEN
//        assertThat(testMeal).isEqualTo(pasta);
//    }

    @Test
    void mealWasNotAbleToBeAdded() {
        //GIVEN
        List<String> pastaIngredients = Arrays.asList("Pasta", "Cheese");
        List<Allergies> allergies = Arrays.asList(Allergies.DAIRY, Allergies.WHEAT);
        Meals pasta = new Meals(1,"Pasta", Difficulty.BEGINNER, allergies, pastaIngredients, "http://www.test_link.com", MealTime.MAIN, null);
        when(fakeMealDao.insertMeal(pasta)).thenReturn(0);

        //THEN
        assertThatThrownBy(() -> {
            //WHEN
            underTest.insertMeal(pasta);
        }).isInstanceOf(RowNotChangedException.class)
                .hasMessage("Meal with id " + 1 + " was not added");
    }

    @Test
    void deleteMeal() {
//        //GIVEN
//        given(fakeMealDao.selectMealById(2)).willReturn(notNull());
//        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
//
//        //WHEN
//        underTest.deleteMeal(2);
//        verify(fakeMealDao).deleteMeals(captor.capture());
//        Integer testValue = captor.getValue();
//
//        //THEN
//        assertThat(testValue).isEqualTo(2);
    }

    @Test
    void updateById() {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    void selectMealByPerson() {
        //GIVEN
        //WHEN
        //THEN
    }
}