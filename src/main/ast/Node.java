package main.ast;

import main.visitors.Visitor;

public abstract class Node {

  public abstract <T> T accept(Visitor<T> v);
}
