package com.will.Food4Thought.chef;

import java.util.List;

public interface ChefDAO {
    int insertChef (Chef chef);
    int deleteChefById (Chef chef);
    int updateChefsById (Integer id, Chef update);
    Chef selectAllChefs (Integer id);
    List<Chef> selectChef();
}
