package main.ast;

import java.util.ArrayList;
import java.util.List;
import main.visitors.Visitor;

public class ItemsNode extends Node {

  private final List<ItemNode> itemList;

  public ItemsNode(ItemNode item) {
    itemList = new ArrayList<>();
    itemList.add(item);
  }

  public ItemsNode(ItemNode item, ItemsNode items) {
    itemList = new ArrayList<>();
    itemList.add(item);
    itemList.addAll(items.getItemList());
  }

  public List<ItemNode> getItemList() {
    return itemList;
  }

  @Override public <T> T accept(Visitor<T> v) {
    return v.visit(this);
  }

}
