package team5.risc.common;

import java.io.Serializable;

public class AreaView  implements Serializable {
    private AreaNode area;
    private AreaNode oldCopy;
    private int id;
    private Map map;
    private boolean isReachable;

    public AreaView(AreaNode a, int id, Map m){
        this.area = a;
        this.id = id;
        this.map = m;
        oldCopy = null;
    }

    public void setBuffer(AreaNode a){
        if(oldCopy == null && a.getOwnerId() != id){
            oldCopy = a;
        }
        else{
            oldCopy = null;
        }
    }

    public AreaNode getArea(){
        return area;
    }

    public void setGrey(boolean isReachable){
        this.isReachable = isReachable;
    }

    public String getGrey(){
        return "Reachable: " + isReachable + "\n"; 
    }

    public String getSpy(){
        boolean isSpy = area.getSpy();
        if(id == area.getOwnerId()){
            isSpy = false;
        }
        return "Spy: " + isSpy + "\n";
    }

    public String getCloaking(){
        boolean cloaking = area.getCloaking();
        if(id == area.getOwnerId()){
            cloaking = false;
        }
        return "Cloak: " + cloaking + "\n";
    }

    public boolean isOld(){
        return oldCopy != null && isReachable == false && area.getSpy() == false;
    }

    public String getOld(){
        boolean out = false;
        if(isOld()){
            out = true;
        }
        return "Old: " + out + "\n";
    }

    public String view(){
        String txt = new String();
        
        AreaNode result = area;
        if(isOld()){
            result = oldCopy;
        }

        txt += result.getName() + "\n";
        txt += "Owner: Player" + result.getOwnerId() + "\n"; 
        txt += result.displayRsrc();
        
        txt += getGrey();
        txt += getSpy();
        txt += getCloaking();
        txt += getOld();
        txt += "\n";
        return txt;
    }

}
