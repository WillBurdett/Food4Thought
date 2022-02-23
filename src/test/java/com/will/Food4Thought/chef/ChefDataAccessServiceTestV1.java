package com.will.Food4Thought.chef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJdbcTest
class ChefDataAccessServiceTestV1 extends AbstractTransactionalTestNGSpringContextTests {

    private ChefDataAccessService underTest;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = new JdbcTemplate();
        underTest = new ChefDataAccessService(jdbcTemplate);
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/chefs-schema.sql")
                .addScript("classpath:jdbc/chefs-test-data.sql")
                .build();
        underTest.setDataSource(dataSource);
    }

    @Test
    void insertChef() {
        Chef testChef = new Chef(5, "Test Chef", "test@chef.com", "TestLocation", 100.00);

        Integer actual = underTest.insertChef(testChef);

        assertEquals(1, actual);

    }

    @Test
    void deleteChefById() {
        Integer actual = underTest.deleteChefById(4);

        assertEquals(1, actual);

    }

    @Test
    void updateChefsById() {
        Chef updateChef = new Chef(1, "Update Chef", "update@chef.com", "UpdateLocation", 100.00);

        Integer actual = underTest.updateChefsById(1, updateChef);

        assertEquals(1, actual);
    }

    @Test
    void selectChefById() {
        Chef expected = new Chef(1, "James Oliver", "jamesoliver@hotmail.com", "London", 300.00);
        Chef actual = underTest.selectChefById(1);

        assertEquals(expected, actual);
    }

    @Test
    void selectAllChefs() {
        Chef chef1 = new Chef(1, "James Oliver", "jamesoliver@hotmail.com", "London", 300.00);
        Chef chef2 = new Chef(2, "Keith Richards", "stayathomeandcook@hotmail.com", "Paris", 60.00);
        Chef chef3 = new Chef(3, "Gordon Ramsey", "gordon@ramsey.com", "London", 500.00);
        Chef chef4 = new Chef(4, "Snoop Stewart", "SnoopStewart@gmail.com", "LA", 50.00);
        List<Chef> expected = List.of(chef1,chef2,chef3,chef4);
        List<Chef> actual = underTest.selectAllChefs();

        assertEquals(expected, actual);
    }
}