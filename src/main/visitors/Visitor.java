package main.visitors;

import java.util.List;
import main.ast.ItemNode;
import main.ast.ItemsNode;
import main.ast.SentFormNode;
import main.ast.StartStateNode;
import main.ast.StateNode;
import main.ast.StatesNode;
import main.ast.TranNode;
import main.ast.TransNode;

public interface Visitor<T> {

  T visit(StartStateNode v);

  T visit(StatesNode v);

  T visit(StateNode v);

  T visit(ItemsNode v);

  T visit(ItemNode v);

  T visit(SentFormNode v);

  T visit(TransNode v);

  T visit(TranNode v);

  T st(Integer id);

  T st(List<Integer> ids);

}
