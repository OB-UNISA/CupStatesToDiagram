package main.visitors;

import java.util.List;
import main.StringTable;
import main.ast.ItemNode;
import main.ast.ItemsNode;
import main.ast.SentFormNode;
import main.ast.StartStateNode;
import main.ast.StateNode;
import main.ast.StatesNode;
import main.ast.TranNode;
import main.ast.TransNode;

public class DebugVisitor implements Visitor<String> {

  private final StringTable stringTable;

  public DebugVisitor(StringTable stringTable) {
    this.stringTable = stringTable;
  }

  @Override public String visit(StartStateNode v) {
    return "START " + v.getStates().accept(this);
  }

  @Override public String visit(StatesNode v) {
    StringBuilder toReturn = new StringBuilder();

    for (StateNode s : v.getStateList()) {
      toReturn.append(s.accept(this));
    }

    return toReturn.toString();
  }

  @Override public String visit(StateNode v) {
    String toReturn = "lalr_state [" + v.getStateNum() + "]: {\n"
        + v.getItems().accept(this)
        + "}\n";

    if (v.getTrans() != null) {
      toReturn += v.getTrans().accept(this);
    }

    toReturn += "\n-------------------\n";

    return toReturn;
  }

  @Override public String visit(ItemsNode v) {
    StringBuilder toReturn = new StringBuilder();

    for (ItemNode i : v.getItemList()) {
      toReturn.append(i.accept(this));
    }

    return toReturn.toString();
  }

  @Override public String visit(ItemNode v) {

    return "  [" + st(v.getNoTerminal()) + "::= "
        + v.getSententialForm().accept(this) + ", "
        + "{" + st(v.getFollows()) + "}]\n";
  }

  @Override public String visit(SentFormNode v) {
    String toReturn = "";
    String dotSym = "(*) ";

    toReturn += switch (v.getDotSymPos()) {
      case BEGIN -> dotSym + st(v.getIds1());
      case END -> st(v.getIds1()) + dotSym;
      case MIDDLE -> st(v.getIds1()) + dotSym + st(v.getIds2());
      case ALONE -> dotSym;
    };

    return toReturn;
  }

  @Override public String visit(TransNode v) {
    StringBuilder toReturn = new StringBuilder();

    for (TranNode s : v.getTranList()) {
      toReturn.append(s.accept(this));
    }

    return toReturn.toString();
  }

  @Override public String visit(TranNode v) {
    return "transition on " + st(v.getId()) + "to state [" + v.getStateNum() + "]\n";
  }

  @Override public String st(Integer id) {
    return stringTable.get(id) + " ";
  }

  @Override public String st(List<Integer> ids) {
    StringBuilder toReturn = new StringBuilder();
    for (Integer i : ids) {
      toReturn.append(stringTable.get(i)).append(" ");
    }
    return toReturn.toString();
  }
}
