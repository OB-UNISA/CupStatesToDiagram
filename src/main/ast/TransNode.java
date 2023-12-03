package main.ast;

import java.util.ArrayList;
import java.util.List;
import main.visitors.Visitor;

public class TransNode extends Node {

  private final List<TranNode> tranList;

  public TransNode(TranNode tran) {
    tranList = new ArrayList<>();
    tranList.add(tran);
  }

  public TransNode(TranNode tran, TransNode trans) {
    tranList = new ArrayList<>();
    tranList.add(tran);
    tranList.addAll(trans.getTranList());
  }

  public List<TranNode> getTranList() {
    return tranList;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }

}
