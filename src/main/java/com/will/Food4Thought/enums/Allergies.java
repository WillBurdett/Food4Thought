package com.will.Food4Thought.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

public enum Allergies {
    DAIRY ("DAIRY","MILK"),
    NUTS ("NUTS","CASHEW"),
    SHELLFISH("SHELLFISH","OYSTER"),
    SOY("SOY","LEGUME"),
    WHEAT("WHEAT","GLUTEN"),
    SESAME("SESAME","SEED");


    private String value1;
    private String value2;


    Allergies(String value1, String value2 ){
        this.value1 = value1;
        this.value2=value2;

    }

    @JsonCreator
    public static Allergies fromString(String type) {
        for (Allergies value : Allergies.values()) {
            if (value.value2.equals(type.toUpperCase())) {
                return value;
            } else if (value.value1.equals(type.toUpperCase())) {
                return value;
            }
        }
        return null;
    }
    @JsonValue
    public String getAllergies() {
        return this.value1.toUpperCase();
    }

//        List<Allergies> allergiesList = new ArrayList<>();
//        int index = 0;
//        for (int i = 0; i < Allergies.values().length; i++) {
//            if (Allergies.values()[i].value1.equals(type.toUpperCase())) {
//                return Allergies.values()[i];
//            } else if (Allergies.values()[i].value2.equals(type.toUpperCase())) {
//                return Allergies.values()[i];
//            }else{
//                allergiesList.set(index++,Allergies.values()[i]);
//                break;
//            }
//        }
//        for (Allergies allergies : allergiesList) {
//            if (allergies.value1.equals(type.toUpperCase())) {
//                return allergies;
//            } else if (allergies.value2.equals(type.toUpperCase())) {
//                return allergies;
//            }
//        }
//        return null;
//    }
//        return phoneType == null
//                ? null
//                : Allergies.valueOf(phoneType.toUpperCase());

}