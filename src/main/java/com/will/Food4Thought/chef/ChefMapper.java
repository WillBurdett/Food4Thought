package com.will.Food4Thought.chef;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChefMapper implements RowMapper<Chef> {

    @Override
    public Chef mapRow(ResultSet resultset, int i) throws SQLException {
        return new Chef(
                resultset.getInt("id"),
                resultset.getString("name"),
                resultset.getString("location"),
                resultset.getString("email"),
                resultset.getDouble("price")
        );
    }
}
