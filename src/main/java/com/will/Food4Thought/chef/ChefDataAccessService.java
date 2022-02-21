package com.will.Food4Thought.chef;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChefDataAccessService implements ChefDAO {

    //Create an instance of jdbctemplate
    private JdbcTemplate jdbcTemplate;
    public ChefDataAccessService (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertChef(Chef chef) {
        return 0;
    }

    @Override
    public int deleteChefById(Integer id) {
        return 0;
    }

    @Override
    public int updateChefsById(Integer id, Chef update) {
        return 0;
    }

    @Override
    public Chef selectChefById(Integer id) {
        return null;
    }

    @Override
    public List<Chef> selectAllChefs() {
        return null;
    }
}
