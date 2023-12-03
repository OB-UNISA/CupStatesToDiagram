package main.ast;

import java.util.ArrayList;
import java.util.List;
import main.visitors.Visitor;

public class StatesNode extends Node {

  private final List<StateNode> stateList;

  public StatesNode(StateNode state) {
    stateList = new ArrayList<>();
    stateList.add(state);
  }

  public StatesNode(StateNode state, StatesNode states) {
    stateList = new ArrayList<>();
    stateList.add(state);
    stateList.addAll(states.getStateList());
  }

  public List<StateNode> getStateList() {
    return stateList;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }

}
