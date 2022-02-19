package com.will.Food4Thought.meal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealsDataAccessService implements MealDAO{

    private JdbcTemplate jdbcTemplate;

    public MealsDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Meals> selectAllMeals() {
        return null;
    }

    @Override
    public Meals selectMealById(Integer id) {
        return null;
    }

    @Override
    public int insertMeal(Meals meal) {
        return 0;
    }

    @Override
    public int deleteMeals(Integer id) {
        return 0;
    }

    @Override
    public int updateMeals(Integer id, Meals update) {
        return 0;
    }
}
