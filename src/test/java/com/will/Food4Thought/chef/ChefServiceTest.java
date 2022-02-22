package com.will.Food4Thought.chef;

import com.will.Food4Thought.chef.chef_exceptions.ChefNotFoundException;
import com.will.Food4Thought.meal.MealDAO;
import com.will.Food4Thought.meal.MealService;
import com.will.Food4Thought.meal.meal_exceptions.MealNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChefServiceTest {

    private ChefService underTest;
    private ChefDAO fakeChefDao;

    @BeforeEach
    void setUp() {
        fakeChefDao = mock(ChefDAO.class);
        underTest = new ChefService(fakeChefDao);
    }

    @Test
    void selectAllChefsReturnsAllChefs(){
        // GIVEN
        Chef chef = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);

        // WHEN
        List<Chef> actual = underTest.selectAllChefs();

        // THEN
        assertThat(actual).isEqualTo(chefs);
    }

    @Test
    void selectAllChefsCanHandleNullList(){
        // GIVEN
        given(fakeChefDao.selectAllChefs()).willReturn(null);

        // WHEN
        assertThatThrownBy(() -> {
            // THEN
            underTest.selectAllChefs();
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("No chef(s) available.");
    }
    @Test
    void selectAllChefsCanHandleEmptyList(){
        List<Chef> test = new ArrayList<>();
        // GIVEN
        given(fakeChefDao.selectAllChefs()).willReturn(test);

        // WHEN
        assertThatThrownBy(() -> {
            // THEN
            underTest.selectAllChefs();
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("No chefs found on system.");
    }

}