package main;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import main.ast.StartStateNode;
import main.visitors.DebugVisitor;

public class ParserTester {

  public static void main(String[] args) throws Exception {
    String filePath =
        args[0] + File.separator + "src" + File.separator + "test_files" + File.separator + args[1];
    StringReader in = new StringReader(Files.readString(Paths.get(filePath)));
    Yylex lexer = new Yylex(in);
    parser p = new parser(lexer);

    StartStateNode ast = (StartStateNode) p.parse().value;
    DebugVisitor debugVisitor = new DebugVisitor(lexer.getStringTable());
    System.out.println(debugVisitor.visit(ast));
  }
}
