package com.will.Food4Thought.meal;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RowNotChangedException extends IllegalStateException{

    public RowNotChangedException(String message){super(message);
    }
}
