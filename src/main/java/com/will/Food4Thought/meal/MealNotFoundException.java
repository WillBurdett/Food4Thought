package com.will.Food4Thought.meal;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MealNotFoundException extends IllegalStateException{

    public MealNotFoundException(String message){super(message);
    }
}
