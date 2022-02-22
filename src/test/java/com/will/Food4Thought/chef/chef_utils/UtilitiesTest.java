package com.will.Food4Thought.chef.chef_utils;

import com.will.Food4Thought.chef.chef_exceptions.EmailInvalidException;
import com.will.Food4Thought.chef.chef_exceptions.PriceInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
    //declares it
    private Utilities underTest;

    @BeforeEach
    void setUp() {
        underTest = new Utilities();
    }

    @Test
    void validateInvalidEmailThrowingException() {
        //GIVEN

        String email = "bobhotmail.com";

        //WHEN
        assertThatThrownBy(() -> {
            underTest.validateEmail(email);
            // THEN
        }).isInstanceOf(EmailInvalidException.class)
                .hasMessage("Please re-enter your email again.");
    }

    @Test
    void validateEmailNotThrowingException() {
        //GIVEN

        String email = "bob@hotmail.com";

        //WHEN

        boolean actual = underTest.validateEmail(email);
        // THEN
        assertThat(actual).isTrue();
    }


    @Test
    void validateInvalidPriceThrowingAnException() {
        //Given
        Double price = 0.00;


        //WHEN
        assertThatThrownBy(() -> {
            underTest.validatePrice(price);
            // THEN
        }).isInstanceOf(PriceInvalidException.class)
                .hasMessage("Please enter a valid price.");
    }


    @Test
    void validateValidPriceNotThrowingAnException() {
        //Given
        Double price = 50.00;


        //WHEN
        boolean actual = underTest.validatePrice(price);
        // THEN
        assertThat(actual).isTrue();

    }

}