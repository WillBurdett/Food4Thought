package com.will.Food4Thought.meal;


import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "meals/{id}")
    public Meals getMealsById(@PathVariable ("id") Integer mealId){
        return mealService.selectMealById(mealId);
    }
    @PostMapping(path = "meals")
    public void insertMeal(@RequestBody Meals meals){
         mealService.insertMeal(meals);
    }


}
