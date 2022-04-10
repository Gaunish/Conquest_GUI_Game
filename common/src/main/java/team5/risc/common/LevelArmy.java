package team5.risc.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public class LevelArmy implements Army, Serializable{
    private int level, no, bonus;
    private List<Integer> cost, bonusList;
    private int owner_id;

    public LevelArmy(int owner_id, int no, int level){
        this.owner_id = owner_id; // owner_id -1 means this area has no owner
        this.level = level;
        this.no = no;
        initBonus();
        setBonus();
        initCost();
    }

    public LevelArmy(int owner_id, int no){
        this.owner_id = owner_id; // owner_id -1 means this area has no owner
        this.level = 0;
        this.no = no;
        initBonus();
        setBonus();
        initCost();
    }

    public void setOwner(int id){
        this.owner_id = id;
    }

    //init bonus list
    public void initBonus(){
        bonusList = Arrays.asList(0, 1, 3, 5, 8, 11, 15);
    }

    //init cost list
    public void initCost(){
        cost = Arrays.asList(0, 3, 11, 30, 55, 90, 140);
    }

    //Upgrade unit to given level
    public void upgradeLevel(int level){
        this.level += level;
        setBonus();
    }

    /*public void inc_unit(int no){
        this.no += no;
    }*/

    //Get cost to upgrade to specified level
    public int cost(int lvl){
        return (cost.get(lvl) - cost.get(level));
    }

    //Set bonus according to level
    public void setBonus(){
        bonus = bonusList.get(level);
    }

    public String getName(){
        return "Level " + level;
    }

    public int getOwnerId(){
        return owner_id;
    }

    public int getUnitNum(){
        return no;
    }

    public int getLevel(){
        return level;
    }

    public int getBonus(){
        return bonus;
    }

    public void mergeArmy(Army toMerge) throws RuntimeException{
        if(owner_id != toMerge.getOwnerId() || level != toMerge.getLevel()){
            throw new UnsupportedOperationException("Armies don't have the same owner/Level. Merge request is refused.");
        }
        no += toMerge.getUnitNum();
    }

    public void removeUnit(int num_to_remove) throws RuntimeException{
        if (no - num_to_remove >= 0) {
            no -= num_to_remove;
        } 
        else {
            no = 0;
            // throw new UnsupportedOperationException("No enough unit to lose");
        }
    }

    public void addUnit(int num_to_add){
        no += num_to_add;
    }

    @Override
    public Army deepCopy() {
        return new LevelArmy(owner_id, no, level);
    }

    public String toString() {
        return "(" + owner_id + ": " + no + ")";
    }

}
