package main.ast;

import java.util.List;
import main.visitors.Visitor;

public class SentFormNode extends Node {

  private final DotSymPos dotSymPos;
  private final List<Integer> ids1;
  private final List<Integer> ids2;

  public SentFormNode() {
    this.dotSymPos = DotSymPos.ALONE;
    this.ids1 = null;
    this.ids2 = null;
  }

  public SentFormNode(DotSymPos dotSymPos, List<Integer> ids1) {
    this.dotSymPos = dotSymPos;
    this.ids1 = ids1;
    this.ids2 = null;
  }

  public SentFormNode(List<Integer> ids1, List<Integer> ids2) {
    this.ids1 = ids1;
    this.ids2 = ids2;
    this.dotSymPos = DotSymPos.MIDDLE;
  }

  public DotSymPos getDotSymPos() {
    return dotSymPos;
  }

  public List<Integer> getIds1() {
    return ids1;
  }

  public List<Integer> getIds2() {
    return ids2;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }
}
