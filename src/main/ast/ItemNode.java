package main.ast;

import java.util.List;
import main.visitors.Visitor;

public class ItemNode extends Node {

  private final Integer noTerminal;
  private final SentFormNode sententialForm;
  private final List<Integer> follows;

  public ItemNode(Integer noTerminal, SentFormNode sententialForm, List<Integer> follows) {
    this.noTerminal = noTerminal;
    this.sententialForm = sententialForm;
    this.follows = follows;
  }

  public Integer getNoTerminal() {
    return noTerminal;
  }

  public SentFormNode getSententialForm() {
    return sententialForm;
  }

  public List<Integer> getFollows() {
    return follows;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }
}
