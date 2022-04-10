package team5.risc.common;

import java.util.LinkedHashSet;

public class Resources {
    private int tot_food, tot_tech;
    
    public Resources(){
        tot_food = 100;
        tot_tech = 100;
    }

    //Method to increment total food/tech
    //after each turn
    public void incFoodTech(LinkedHashSet<AreaNode> areas){
        for(AreaNode a : areas){
          tot_food += a.getFood();
          tot_tech += a.getTech();
        }
    }

    public int getFood(){
        return tot_food;
    }
    
    public int getTech(){
        return tot_tech;
    }
    
    //Method to check if food < tot_food
    public boolean isFoodValid(int food){
        //System.out.println(food);
        return (tot_food - food) >= 0;
    }
    
    //Method to check if tech < tot_tech
    public boolean isTechValid(int tech){
        return (tot_tech - tech) >= 0;
    }

    public boolean subFood(int food){
        if(isFoodValid(food)){
            tot_food -= food;
            return true;
        }
        return false;
    }
    
    public boolean subTech(int tech){
        if(isTechValid(tech)){
            tot_tech -= tech;
            return true;
        }
        return false;
    }
}
