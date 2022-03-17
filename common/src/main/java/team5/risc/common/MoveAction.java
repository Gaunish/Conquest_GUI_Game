package team5.risc.common;

public class MoveAction extends SimpleAction {
  public MoveAction(String source, String dest, int num_unit){
    this.src = new AreaNode(source);
    this.dest = new AreaNode(dest);
    this.num_unit = num_unit;
  }

  public String getStr(){
    String prompt = src.getName() + " -> " + dest.getName() + ": " + num_unit;
    return prompt;
  }
}
