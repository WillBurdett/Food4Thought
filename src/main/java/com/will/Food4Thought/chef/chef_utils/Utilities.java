package com.will.Food4Thought.chef.chef_utils;

import com.will.Food4Thought.chef.chef_exceptions.EmailInvalidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {


    public static void main(String[] args) {



    }
        public static boolean validateEmail (String email) {
        //  [\\w-] - any character (letter or number) & contain -
         //   {1,20} any number of character between 1-20
         //@\w{2,20} between 2-20 character after the @
         // .w{2,3} between 2-3 characters after the dot.
            String regex = "[\\w-]{1,20}@\\w{2,20}\\.\\w{2,3}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();

            }



    // check the price input for chef is above 0
    public static boolean minimumPrice (Double price) {
        if (Double price <= 0){
            throw new
        }
    }

}


