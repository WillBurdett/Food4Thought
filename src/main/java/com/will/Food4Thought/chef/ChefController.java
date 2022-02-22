package com.will.Food4Thought.chef;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChefController {
    private ChefService chefService;

    public ChefController(ChefService chefService){
        this.chefService=chefService;
    }

    @GetMapping(path="chefs")
    public List<Chef> getAllChefs (){
       return chefService.selectAllChefs();
   }

    @GetMapping(path ="chefs/{id}")
    public Chef getChefById(@PathVariable("id") Integer chefId){
        return chefService.selectChefById(chefId);
   }

    @PostMapping(path="chefs")
    public void insertChef(@RequestBody Chef chef){
        chefService.insertChef(chef);
}

    @DeleteMapping(path="chefs/{id}")
    public void deleteChefById(@PathVariable("id")Integer chefId){
        chefService.deleteChef(chefId);
}

    @PutMapping(path="chefs/{id}")
    public void updateChefsById(@PathVariable("id") Integer chefId, @RequestBody Chef update){
        chefService.updateChefsById(chefId,update);
}

}
