package main.ast;

import main.visitors.Visitor;

public class StateNode extends Node {

  private final String stateNum;

  private final ItemsNode items;
  private TransNode trans;

  public StateNode(String stateNum, ItemsNode items, TransNode trans) {
    this.stateNum = stateNum;
    this.items = items;
    this.trans = trans;
  }

  public StateNode(String stateNum, ItemsNode items) {
    this.stateNum = stateNum;
    this.items = items;
  }

  public String getStateNum() {
    return stateNum;
  }

  public ItemsNode getItems() {
    return items;
  }

  public TransNode getTrans() {
    return trans;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }
}
