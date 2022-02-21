package com.will.Food4Thought.chef;

import com.will.Food4Thought.chef.chef_exceptions.ChefNotFoundException;
import com.will.Food4Thought.chef.chef_exceptions.EmailInvalidException;
import com.will.Food4Thought.meal.RowNotChangedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    ChefDAO chefDAO;

    public ChefService(ChefDAO chefDAO) {
        this.chefDAO = chefDAO;
    }

    //Selecting all Chefs
    public List<Chef> selectAllChefs() {
        if (chefDAO.selectAllChefs() == null) {
            throw new IllegalStateException("No chef(s) available.");
        }
        return chefDAO.selectAllChefs();
    }

    //Selecting Chef By ID
    public Chef selectChefById(Integer chefId) {
        try {
            return chefDAO.selectChefById(chefId);
        } catch (IllegalStateException e) {

            throw new ChefNotFoundException("Chef not found by id " + chefId + ".");
        }
    }

    // Checking if the chef is already in the database by email entered
    public boolean checkIfEmailIsThere (String email){
        for (Chef selectAllChef : chefDAO.selectAllChefs()) {
            if (selectAllChef.getEmail().equals(email)) {
                throw new EmailInvalidException("Email already on system.");
            }
        }
        return true;
    }

    //Inserting a Chef
    public void insertChef (Chef chefs){
        if (checkIfEmailIsThere(chefs.getEmail())) {
            chefDAO.insertChef(chefs);
        } else if (chefDAO.insertChef(chefs) != 1) {
                    throw new RowNotChangedException("Chef " + chefs.getName() + " not added.");
        }
    }

    //Deleting Chef by ID
    public void deleteChef (Integer chefId){
        if (chefDAO.selectChefById(chefId) != null) {
            chefDAO.deleteChefById(chefId);
        } else if (chefDAO.deleteChefById(chefId) != 1) {
            throw new RowNotChangedException("Chef with id " + chefId + "was not deleted.");
        }
    }

    //Updating Chef by ID
    public void updateChefsById (Integer chefId, Chef update){
        if (chefDAO.selectChefById(chefId) != null ) {
            chefDAO.updateChefsById(chefId, update);
        } else if (chefDAO.updateChefsById(chefId, update) != 1) {
            throw new RowNotChangedException("Chef with id " + chefId + " was not updated.");
        }
    }
}
