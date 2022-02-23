package com.will.Food4Thought.chef;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChefDataAccessService implements ChefDAO {

    private JdbcTemplate jdbcTemplate;
    public ChefDataAccessService (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //setDataSource method added for the ChefDataAccessService Tests
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //----------------------------------------------------------------

    @Override
    public int insertChef(Chef chef) {
        String sql = """
                INSERT INTO chefs (name, email, location, price)
                VALUES (?, ?, ?, ?)
                """;
        return jdbcTemplate.update(sql,
                chef.getName(),chef.getEmail(),chef.getLocation(),chef.getPrice());
    }

    @Override
    public int deleteChefById(Integer id) {
        String sql = "DELETE FROM chefs WHERE id=?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected;
    }

    @Override
    public int updateChefsById(Integer id, Chef update) {
        var sql = """
                UPDATE chefs SET name = ?, email = ?, location = ?, price = ? WHERE id = ?;
                """;
        int rowAffected = jdbcTemplate.update(sql, update.getName(), update.getEmail(), update.getLocation(), update.getPrice(),
                id);
        if (rowAffected ==1){
            return 1;
        }
        return 0;
    }

    @Override
    public Chef selectChefById(Integer id) {
        var sql = """
                SELECT id, name, email, location, price FROM chefs WHERE chefs.id = ?;
                """;
        return jdbcTemplate.queryForObject(sql, new ChefMapper(), id);
    }

    @Override
    public List<Chef> selectAllChefs() {
        var sql = """
                SELECT id, name, email, location, price FROM chefs
                """;
        return jdbcTemplate.query(sql, new ChefMapper());
    }
}
