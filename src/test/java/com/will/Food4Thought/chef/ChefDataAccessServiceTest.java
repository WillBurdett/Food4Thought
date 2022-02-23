package com.will.Food4Thought.chef;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChefDataAccessServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private ChefDataAccessService underTest;


    @Test
    void insertChef() {
        //Given
//        JdbcTemplate jdbcTemplate=new JdbcTemplate();
//        ChefDataAccessService underTest=new ChefDataAccessService(jdbcTemplate);
        Chef chef = new Chef(1,"james", "james@gmail.com", "london",22.0);
        underTest.insertChef(chef);
        
        Chef actual = null;
        for (Chef selectAllChef : underTest.selectAllChefs()) {
            if(selectAllChef.getName().equals("james"));
            actual=selectAllChef;
        }
        assertThat(chef).isEqualTo(actual);

    }


}