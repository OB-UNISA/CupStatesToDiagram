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

// https://graphviz.org/Gallery/directed/psg.html
public class GraphvizVisitor implements Visitor<String> {

  private final StringTable stringTable;
  private String trans;
  private String currentState;

  public GraphvizVisitor(StringTable stringTable) {
    this.stringTable = stringTable;
    this.trans = "";
  }

  @Override public String visit(StartStateNode v) {
    return "digraph g {\n"
        + "  fontname=\"Helvetica,Arial,sans-serif\"\n"
        + "  node [fontname=\"Helvetica,Arial,sans-serif\"]\n"
        + "  edge [fontname=\"Helvetica,Arial,sans-serif\"]\n"
        + "  graph [fontsize=30 labelloc=\"t\" label=\"\" splines=true overlap=false ];\n"
        + "  ratio = auto;\n" + v.getStates().accept(this) + trans + "}";
  }

  @Override public String visit(StatesNode v) {
    StringBuilder toReturn = new StringBuilder();

    for (StateNode s : v.getStateList()) {
      toReturn.append(s.accept(this));
    }

    return toReturn.toString();
  }

  @Override public String visit(StateNode v) {
    currentState = v.getStateNum();
    String toReturn = "\"state" + v.getStateNum()
        + "\" [ style = \"filled\" penwidth = 1 fillcolor = \"white\" fontname = \"Courier New\" "
        + "shape = \"Mrecord\" label =<<table border=\"0\" cellborder=\"0\" cellpadding=\"3\" "
        + "bgcolor=\"white\"><tr><td bgcolor=\"black\" align=\"center\" colspan=\"2\">"
        + "<font color=\"white\">State #" + v.getStateNum() + "</font></td></tr>"
        + v.getItems().accept(this)
        + "</table>> ];\n";

    if (v.getTrans() != null) {
      trans += v.getTrans().accept(this);
    }

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

    return "<tr><td align=\"left\">" + st(v.getNoTerminal()) + "-&gt; "
        + v.getSententialForm().accept(this) + "</td><td bgcolor=\"lightgrey\" align=\"left\">"
        + st(v.getFollows()) + "</td></tr>";
  }

  @Override public String visit(SentFormNode v) {
    String toReturn = "";
    String dotSym = "&bull; ";

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
    return "state" + currentState + "-> state" + v.getStateNum()
        + "[ penwidth = 1 fontsize = 14 fontcolor = \"black\" label = \"" + st(v.getId())
        + "\"];\n";
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
