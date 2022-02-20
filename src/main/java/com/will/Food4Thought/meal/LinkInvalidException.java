package com.will.Food4Thought.meal;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LinkInvalidException extends IllegalStateException{

    public LinkInvalidException(String message){super(message);
    }
}
