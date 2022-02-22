package com.will.Food4Thought.meal.meal_utils;

import com.will.Food4Thought.enums.Allergies;

import java.util.ArrayList;
import java.util.List;

public class MealUtilities {

    public String ingredientsInfo (List<String> ingredients){
        if (ingredients == null){

            return null;
        }
        String output = "";
        for ( String ingredient : ingredients) {
            output+= ingredient + ",";
        }
        return output.substring(0, output.length() - 1);
    }

    public  String allergyInfo (List <Allergies> allergies){
        if (allergies == null){
            return null;
        }
        String output = "";
        for (Allergies allergy : allergies) {
            output+= allergy + ",";
        }
        return output.substring(0, output.length() - 1);
    }

    public static List<Allergies> allergies(String input){
        List <Allergies> allergies = new ArrayList<>();
        if (input == null){
            return null;
        }
        for (String s : input.split(",")) {
            Allergies allergy = Allergies.valueOf(s.toUpperCase().replaceAll(" ", ""));
            allergies.add(allergy);
        }
        return allergies;
    }
}
