package com.will.Food4Thought.chef;

import com.will.Food4Thought.chef.chef_exceptions.ChefNotFoundException;
import com.will.Food4Thought.meal.MealDAO;
import com.will.Food4Thought.meal.MealService;
import com.will.Food4Thought.meal.meal_exceptions.MealNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ChefServiceTest {

    private ChefService underTest;
    private ChefDAO fakeChefDao;

    @BeforeEach
    void setUp() {
        fakeChefDao = mock(ChefDAO.class);
        underTest = new ChefService(fakeChefDao);
    }

    @Test
    void selectAllChefs_ReturnsAllChefs(){
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
    void selectAllChefs_CanHandleNullList(){
        // GIVEN
        given(fakeChefDao.selectAllChefs()).willReturn(null);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.selectAllChefs();
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("No chef(s) available.");
    }
    @Test
    void selectAllChefs_CanHandleEmptyList(){
        List<Chef> test = new ArrayList<>();
        // GIVEN
        given(fakeChefDao.selectAllChefs()).willReturn(test);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.selectAllChefs();
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("No chefs found on system.");
    }

    @Test
    void selectChefById_ReturnsCorrectChef(){
        // GIVEN
        Chef chef = new Chef(1, "Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);
        when(fakeChefDao.selectChefById(1)).thenReturn(chef);

        // WHEN
        Chef actual = underTest.selectChefById(1);
        Chef expected = chef;

        // THEN
        verify(fakeChefDao).selectChefById(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void selectChefById_CanHandleIdThatDoesNotExist(){
        // GIVEN
        Chef chef = new Chef(1, "Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.selectChefById(2);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef not found by id 2.");
        verify(fakeChefDao, never()).selectChefById(2);
    }

    @Test
    void checkIfEmailIsUnique_ThrowsExceptionIfNot(){
        // GIVEN
        Chef chef = new Chef(1, "Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.checkIfEmailIsUnique(chef.getEmail());
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Email already on system.");
        verify(fakeChefDao, never()).insertChef(chef);
    }

    @Test
    void checkIfEmailIsUnique_ReturnsTrueIfUnique(){
        // GIVEN
        Chef chef = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);

        // WHEN
        Boolean actual = underTest.checkIfEmailIsUnique("unique_email@gmail.com");
        Boolean expected = true;

        // THEN
        assertEquals(actual, expected);
    }

    @Test
    void insertChef_AddsChefWhenValidChef(){
        // GIVEN
        Chef chef1 = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        Chef chef2 = new Chef("Melissa", "melissa@gmail.com", "Wandsworth Town", 25.00);
        List<Chef> chefs = Arrays.asList(chef1);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);
        when(fakeChefDao.insertChef(chef2)).thenReturn(1);

        // WHEN
        underTest.insertChef(chef2);

        // THEN
        verify(fakeChefDao).insertChef(chef2);
    }

    @Test
    void insertChef_ThrowsExceptionIfRowUnchanged(){
        // GIVEN
        Chef chef1 = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        Chef chef2 = new Chef("Melissa", "melissa@gmail.com", "Wandsworth Town", 25.00);
        List<Chef> chefs = Arrays.asList(chef1);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);
        when(fakeChefDao.insertChef(chef2)).thenReturn(0);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.insertChef(chef2);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef Melissa not added.");
        verify(fakeChefDao).insertChef(chef2);
    }
}