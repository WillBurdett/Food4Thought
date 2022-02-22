package com.will.Food4Thought.chef.chef_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ChefNotFoundException extends IllegalStateException {

    public ChefNotFoundException(String message) {
        super(message);

    }
}