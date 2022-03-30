package team5.risc.common;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public class ArmyUnit {
    private int level, no, bonus;
    private List<Integer> cost;

    public ArmyUnit(int level, int no){
        this.level = level;
        this.no = no;
        setBonus();
        initCost();
    }

    //init cost list
    public void initCost(){
        cost = Arrays.asList(0, 3, 8, 19, 25, 35, 50);
    }

    //Upgrade unit to given level
    public void upgradeLevel(int level){
        this.level += level;
        setBonus();
    }

    public int getNo(){
        return no;
    }

    public void inc_unit(int no){
        this.no += no;
    }

    //Subtract given no of units
    //Returns true if valid
    public boolean dec_unit(int no){
        if(this.no >= no){
            this.no -= no;
            return true;
        }
        return false;
    }

    //Get cost to upgrade to specified level
    public int cost(int lvl){
        return (cost.get(lvl) - cost.get(level));
    }

    //Set bonus according to level
    public void setBonus(){
        if(level > 0 && level <= 3){
            bonus = level*2 - 1;
        }
        else{
            bonus = (level*2) + (level % 2);
        }
    }
}
