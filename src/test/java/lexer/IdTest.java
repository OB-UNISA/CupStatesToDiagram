package test.java.lexer;

import java.io.IOException;
import java.io.StringReader;
import java_cup.runtime.Symbol;
import main.Yylex;
import main.sym;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdTest {


  @Test
  public void valid() throws IOException {
    StringReader source = new StringReader("Vars ::= Var");
    Yylex lexer = new Yylex(source);
    Symbol token = lexer.next_token();
    Assertions.assertEquals(sym.ID, token.sym);
    Assertions.assertEquals(1, token.value);

    source = new StringReader("Var Vars");
    lexer = new Yylex(source);
    token = lexer.next_token();
    Assertions.assertEquals(sym.ID, token.sym);
    Assertions.assertEquals(1, token.value);
    token = lexer.next_token();
    Assertions.assertEquals(sym.ID, token.sym);
    Assertions.assertEquals(2, token.value);
  }

  @Test
  public void invalid() throws IOException {
    StringReader source = new StringReader("4a ^= 4");
    Yylex lexer = new Yylex(source);
    Symbol token = lexer.next_token();
    Assertions.assertEquals(sym.SNUM, token.sym);
  }
}
