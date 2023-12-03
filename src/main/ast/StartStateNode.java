package main.ast;

import main.visitors.Visitor;

public class StartStateNode extends Node {

  private final StatesNode states;

  public StartStateNode(StatesNode states) {
    this.states = states;
  }

  public StatesNode getStates() {
    return states;
  }


  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }
}
