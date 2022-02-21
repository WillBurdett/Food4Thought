package com.will.Food4Thought.chef;

import java.util.List;

public interface ChefDAO {
    int insertChef (Chef chef);
    int deleteChefById (Integer id);
    int updateChefsById (Integer id, Chef update);
    Chef selectChefById (Integer id);
    List<Chef> selectAllChefs();
}
