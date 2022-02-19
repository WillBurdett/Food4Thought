package com.will.Food4Thought.meal;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MealController {

    private MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping(path = "meals")
    public List<Meals> getAllMeals(){
        return mealService.selectAllMeals();
    }

}