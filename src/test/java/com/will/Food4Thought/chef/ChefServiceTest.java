package com.will.Food4Thought.chef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
    void insertChef_ThrowsExceptionIfEmailAlreadyExists(){
        // GIVEN
        Chef chef1 = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        Chef chef2 = new Chef("Melissa", "bob@aol.com", "Wandsworth Town", 25.00);
        List<Chef> chefs = Arrays.asList(chef1);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);
        when(fakeChefDao.insertChef(chef2)).thenReturn(1);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.insertChef(chef2);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Email already on system.");
        verify(fakeChefDao, never()).insertChef(chef2);
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

    @Test
    void DeleteChef_DeletesChefWithCorrectId(){
        // GIVEN
        Chef chef1 = new Chef(1, "Bob", "bob@aol.com", "Brixton", 25.00);
        when(fakeChefDao.selectChefById(1)).thenReturn(chef1);
        when(fakeChefDao.deleteChefById(1)).thenReturn(1);

        // WHEN
        underTest.deleteChef(1);

        // THEN
        verify(fakeChefDao).deleteChefById(1);
    }

    @Test
    void DeleteChef_ThrowsExceptionIfIdNotFound(){
        // GIVEN
        Chef chef1 = new Chef("Bob", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef1);
        when(fakeChefDao.selectAllChefs()).thenReturn(chefs);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.deleteChef(2);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef with id 2 could not be found.");
        verify(fakeChefDao, never()).deleteChefById(2);
    }

    @Test
    void DeleteChef_ThrowsExceptionIfRowUnchanged(){
        // GIVEN
        Chef chef1 = new Chef(1,"Bob", "bob@aol.com", "Brixton", 25.00);
        when(fakeChefDao.selectChefById(1)).thenReturn(chef1);
        when(fakeChefDao.deleteChefById(1)).thenReturn(0);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.deleteChef(1);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef with id 1 was not deleted.");
        verify(fakeChefDao).deleteChefById(1);
    }
    @Test
    void updateChefsById_UpdatesChefWithValidId(){
        // GIVEN
        Chef chef1 = new Chef(1, "Bob", "bob@aol.com", "Brixton", 25.00);
        Chef chef1updated = new Chef(1, "Bob", "bob@aol.com", "Ealing", 25.00);
        when(fakeChefDao.selectChefById(1)).thenReturn(chef1);
        when(fakeChefDao.updateChefsById(1, chef1updated)).thenReturn(1);

        // WHEN
        underTest.updateChefsById(1, chef1updated);

        // THEN
        verify(fakeChefDao).updateChefsById(1, chef1updated);
    }

    @Test
    void updateChefsById_CanHandleIdDoesNotExist() {
        // GIVEN
        Chef chef1 = new Chef(1,"Bob", "bob@aol.com", "Brixton", 25.00);
        Chef chef1updated = new Chef(1,"Bobby", "bob@aol.com", "Brixton", 25.00);
        List<Chef> chefs = Arrays.asList(chef1);
        when(fakeChefDao.selectChefById(2)).thenReturn(null);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.updateChefsById(2, chef1updated);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef with id 2 could not be found.");
        verify(fakeChefDao, never()).updateChefsById(2, chef1updated);
    }

    @Test
    void updateChefsById_ThrowsExceptionIfRowUnchanged() {
        // GIVEN
        Chef chef1 = new Chef(1,"Bob", "bob@aol.com", "Brixton", 25.00);
        when(fakeChefDao.selectChefById(1)).thenReturn(chef1);
        when(fakeChefDao.updateChefsById(1, chef1)).thenReturn(0);

        // WHEN
        assertThatThrownBy(() -> {
            underTest.updateChefsById(1, chef1);
            // THEN
        }).isInstanceOf(IllegalStateException.class)
                .hasMessage("Chef with id 1 was not updated.");
        verify(fakeChefDao).updateChefsById(1, chef1);
    }
}