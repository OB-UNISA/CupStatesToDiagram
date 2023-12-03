package test.java.lexer;

import java.io.IOException;
import java.io.StringReader;
import main.Yylex;
import main.sym;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LexerTest {


  @Test
  public void valid() throws IOException {
    StringReader source = new StringReader("Vars ::= Var");
    Yylex lexer = new Yylex(source);
    Assertions.assertEquals(sym.ID, lexer.next_token().sym);
    Assertions.assertEquals(sym.DERSYM, lexer.next_token().sym);
    Assertions.assertEquals(sym.ID, lexer.next_token().sym);
    Assertions.assertEquals(sym.EOF, lexer.next_token().sym);

  }

  @Test
  public void invalid() throws IOException {
    StringReader source = new StringReader("Vars := Var");
    Yylex lexer = new Yylex(source);
    Assertions.assertEquals(sym.ID, lexer.next_token().sym);
    Assertions.assertEquals(sym.COLON, lexer.next_token().sym);
    Assertions.assertEquals(sym.error, lexer.next_token().sym);
  }
}
