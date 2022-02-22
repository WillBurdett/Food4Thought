package com.will.Food4Thought.chef.chef_utils;

import com.will.Food4Thought.chef.chef_exceptions.EmailInvalidException;
import com.will.Food4Thought.chef.chef_exceptions.PriceInvalidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static boolean validateEmail(String email) {
        //  [\\w-] - any character (letter or number) & contain -
        //   {1,20} any number of character between 1-20
        //@\w{2,20} between 2-20 character after the @
        // .w{2,3} between 2-3 characters after the dot.
        String regex = "[\\w-]{1,20}@\\w{2,20}\\.\\w{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new EmailInvalidException("Please re-enter your email again.");
        }
        return true;

    }

    public static boolean validatePrice(Double price) {
        if (price <= 0) {
            throw new PriceInvalidException("Please enter a valid price.");
        }
        return true;
    }
}

