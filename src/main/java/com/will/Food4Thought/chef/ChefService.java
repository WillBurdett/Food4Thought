package com.will.Food4Thought.chef;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChefService {

    private ChefDAO chefDAO;

    public ChefService(@Qualifier("postgres") ChefDAO chefDAO) {
        this.chefDAO = chefDAO;
    }
//Selecting all Chefs
    public List<Chef> selectAllChefs() {
        if (chefDAO.selectAllChefs() == null) {
            throw new IllegalStateException("no chef available");
        }
        return chefDAO.selectAllChefs();
    }
   //Selecting Chef By ID
   public Chef selectChefById(Integer chefId) {
   try { return chefDAO.selectAllChefs(chefId);
   } catch (IllegalStateException e){
       throw new IllegalStateException("chef not found by Id");
    }

    //Inserting a Chef
       //do we need to check if the Chef is already insert ???
    public void insertChef(Chef chef) {
      int rowAffected = if (chefDAO.insertChef(chef)==1);

       }
    }



    public void deleteChef(Integer chefId) {
        try { (chefDAO.selectAllChefs(chefId)!=null)
    if (chefDAO.selectAllChefs(chefId)!=null) {
        chefDAO.deleteChefById(chefId);
    }
    catch { (IllegalStateException e)
throw new IllegalStateException( "chef not deleted");
            }
        }
    }

    public void updateChefsById(Integer chefId, Chef update) {
    }

    public void insertChef(Chef chef) {
    }
}
