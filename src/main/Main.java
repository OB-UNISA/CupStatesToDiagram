package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import main.ast.StartStateNode;
import main.visitors.DebugVisitor;
import main.visitors.GraphvizVisitor;
import main.visitors.PlantUMLObjVisitor;
import main.visitors.Visitor;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.MutuallyExclusiveGroup;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

  public static void main(String[] args) throws Exception {
    ArgumentParser parser = ArgumentParsers.newFor("CupStatesToDiagram").build().description(
        "Translate Cup's states in the debug output to diagrams.");
    parser.addArgument("-i").type(Arguments.fileType().verifyCanRead())
        .help("Take the input from a file.");
    parser.addArgument("-o").type(Arguments.fileType().verifyCanCreate())
        .help("Output to a file.");

    MutuallyExclusiveGroup visitorGroup =
        parser.addMutuallyExclusiveGroup("Diagram type");
    visitorGroup.addArgument("--graphviz").action(Arguments.storeTrue())
        .help("Create a Graphviz diagram in dot language. (Default)");
    visitorGroup.addArgument("--plantuml").action(Arguments.storeTrue())
        .help("Create a PlantUML diagram in its language.");
    visitorGroup.addArgument("--debug").action(Arguments.storeTrue())
        .help("Debug the Lexer and Parser. The input will run on both "
            + "and produce the Cup's states debug output.");

    Namespace ns = parser.parseArgsOrFail(args);

    File fileInput = ns.get("i");
    Reader reader;

    if (fileInput == null) {
      System.out.println("Paste the Cup's states debug output, hit Return, then Cmd-D (in MacOs) "
          + "or Ctrl-D (in Windows)");
      InputStreamReader inp = new InputStreamReader(System.in);
      reader = new BufferedReader(inp);
    } else {
      reader = new StringReader(Files.readString(fileInput.toPath()));
    }

    Yylex lexer = new Yylex(reader);
    parser p = new parser(lexer);
    StartStateNode ast = (StartStateNode) p.parse().value;
    StringTable st = lexer.getStringTable();

    Visitor<String> visitor;

    if (Boolean.TRUE.equals(ns.getBoolean("plantuml"))) {
      visitor = new PlantUMLObjVisitor(st);
    } else if (Boolean.TRUE.equals(ns.getBoolean("debug"))) {
      visitor = new DebugVisitor(st);
    } else {
      visitor = new GraphvizVisitor(st);
    }

    String visitorRes = visitor.visit(ast);
    File fileOutput = ns.get("o");

    if (fileOutput == null) {
      System.out.println(visitorRes);
    } else {
      try (FileWriter fileWriter = new FileWriter(fileOutput)) {
        fileWriter.write(visitorRes);
      }
    }


  }
}
