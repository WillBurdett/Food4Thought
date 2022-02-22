package com.will.Food4Thought.meal.meal_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MealNotAddedException extends IllegalStateException {

    public MealNotAddedException(String message){super(message);
    }
}
