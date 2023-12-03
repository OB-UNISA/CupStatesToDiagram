package main.ast;

import main.visitors.Visitor;

public class TranNode extends Node {

  private final Integer id;
  private final String stateNum;

  public TranNode(Integer id, String stateNum) {
    this.id = id;
    this.stateNum = stateNum;
  }

  public Integer getId() {
    return id;
  }

  public String getStateNum() {
    return stateNum;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }
}
