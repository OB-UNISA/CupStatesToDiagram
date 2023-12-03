package test.java.parser;

import java.io.StringReader;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;
import main.Yylex;
import main.ast.StartStateNode;
import main.parser;
import main.visitors.DebugVisitor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {


  @Test
  public void valid() throws Exception {
    StringReader in = new StringReader(INPUT_1);
    Yylex lexer = new Yylex(in);
    ScannerBuffer scannerBuffer = new ScannerBuffer(lexer);
    parser p = new parser(scannerBuffer);

    StartStateNode ast = (StartStateNode) p.parse().value;
    DebugVisitor debugVisitor = new DebugVisitor(lexer.getStringTable());
    String debugOutput = debugVisitor.visit(ast);

    Yylex newLexer = new Yylex(new StringReader(debugOutput));
    ScannerBuffer newScannerBuffer = new ScannerBuffer(newLexer);
    parser newP = new parser(newScannerBuffer);
    newP.parse();

    Assertions.assertEquals(scannerBuffer.getBuffered().size(),
        newScannerBuffer.getBuffered().size());

    int tokensSize = scannerBuffer.getBuffered().size();
    for (int i = 0; i < tokensSize; i++) {
      Symbol s1 = scannerBuffer.next_token();
      Symbol s2 = newScannerBuffer.next_token();
      Assertions.assertEquals(s1.sym, s2.sym);
      Assertions.assertEquals(s1.value, s2.value);
    }

  }

  // @formatter:off
  private static final String INPUT_1 =
"""
START lalr_state [0]: {
  [$START ::= (*) StartState EOF , {EOF }]
  [StartState ::= (*) START States , {EOF }]
}
transition on START to state [2]
transition on StartState to state [1]
  
-------------------
lalr_state [1]: {
  [$START ::= StartState (*) EOF , {EOF }]
}
transition on EOF to state [43]
  
-------------------
lalr_state [2]: {
  [State ::= (*) LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
  [States ::= (*) State States , {EOF }]
  [State ::= (*) LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [States ::= (*) State , {EOF }]
  [StartState ::= START (*) States , {EOF }]
}
transition on LALRWRD to state [5]
transition on State to state [4]
transition on States to state [3]
  
-------------------
lalr_state [3]: {
  [StartState ::= START States (*) , {EOF }]
}
  
-------------------
lalr_state [4]: {
  [State ::= (*) LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
  [States ::= State (*) States , {EOF }]
  [States ::= (*) State States , {EOF }]
  [State ::= (*) LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [States ::= State (*) , {EOF }]
  [States ::= (*) State , {EOF }]
}
transition on LALRWRD to state [5]
transition on State to state [4]
transition on States to state [42]
  
-------------------
lalr_state [5]: {
  [State ::= LALRWRD (*) LSQ SNUM RSQ COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
  [State ::= LALRWRD (*) LSQ SNUM RSQ COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
}
transition on LSQ to state [6]
  
-------------------
lalr_state [6]: {
  [State ::= LALRWRD LSQ (*) SNUM RSQ COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [State ::= LALRWRD LSQ (*) SNUM RSQ COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
}
transition on SNUM to state [7]
  
-------------------
lalr_state [7]: {
  [State ::= LALRWRD LSQ SNUM (*) RSQ COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [State ::= LALRWRD LSQ SNUM (*) RSQ COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
}
transition on RSQ to state [8]
  
-------------------
lalr_state [8]: {
  [State ::= LALRWRD LSQ SNUM RSQ (*) COLON LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [State ::= LALRWRD LSQ SNUM RSQ (*) COLON LCUR Items RCUR SEP , {EOF LALRWRD }]
}
transition on COLON to state [9]
  
-------------------
lalr_state [9]: {
  [State ::= LALRWRD LSQ SNUM RSQ COLON (*) LCUR Items RCUR Trans SEP , {EOF LALRWRD }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON (*) LCUR Items RCUR SEP , {EOF LALRWRD }]
}
transition on LCUR to state [10]
  
-------------------
lalr_state [10]: {
  [Item ::= (*) LSQ ID DERSYM SentForm COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
  [Items ::= (*) Item , {RCUR }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR (*) Items RCUR SEP , {EOF LALRWRD }]
  [Items ::= (*) Item Items , {RCUR }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR (*) Items RCUR Trans SEP , {EOF LALRWRD }]
}
transition on Items to state [13]
transition on Item to state [12]
transition on LSQ to state [11]
  
-------------------
lalr_state [11]: {
  [Item ::= LSQ (*) ID DERSYM SentForm COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
}
transition on ID to state [27]
  
-------------------
lalr_state [12]: {
  [Item ::= (*) LSQ ID DERSYM SentForm COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
  [Items ::= Item (*) , {RCUR }]
  [Items ::= (*) Item , {RCUR }]
  [Items ::= Item (*) Items , {RCUR }]
  [Items ::= (*) Item Items , {RCUR }]
}
transition on Items to state [26]
transition on Item to state [12]
transition on LSQ to state [11]
  
-------------------
lalr_state [13]: {
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items (*) RCUR SEP , {EOF LALRWRD }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items (*) RCUR Trans SEP , {EOF LALRWRD }]
}
transition on RCUR to state [14]
  
-------------------
lalr_state [14]: {
  [Trans ::= (*) Tran , {SEP }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR (*) SEP , {EOF LALRWRD }]
  [Trans ::= (*) Tran Trans , {SEP }]
  [Tran ::= (*) TRANWRD ID STATEWRD LSQ SNUM RSQ , {TRANWRD SEP }]
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR (*) Trans SEP , {EOF LALRWRD }]
}
transition on Trans to state [18]
transition on TRANWRD to state [17]
transition on Tran to state [16]
transition on SEP to state [15]
  
-------------------
lalr_state [15]: {
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR SEP (*) , {EOF LALRWRD }]
}
  
-------------------
lalr_state [16]: {
  [Trans ::= Tran (*) , {SEP }]
  [Trans ::= (*) Tran , {SEP }]
  [Trans ::= Tran (*) Trans , {SEP }]
  [Trans ::= (*) Tran Trans , {SEP }]
  [Tran ::= (*) TRANWRD ID STATEWRD LSQ SNUM RSQ , {TRANWRD SEP }]
}
transition on Trans to state [25]
transition on TRANWRD to state [17]
transition on Tran to state [16]
  
-------------------
lalr_state [17]: {
  [Tran ::= TRANWRD (*) ID STATEWRD LSQ SNUM RSQ , {TRANWRD SEP }]
}
transition on ID to state [20]
  
-------------------
lalr_state [18]: {
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR Trans (*) SEP , {EOF LALRWRD }]
}
transition on SEP to state [19]
  
-------------------
lalr_state [19]: {
  [State ::= LALRWRD LSQ SNUM RSQ COLON LCUR Items RCUR Trans SEP (*) , {EOF LALRWRD }]
}
  
-------------------
lalr_state [20]: {
  [Tran ::= TRANWRD ID (*) STATEWRD LSQ SNUM RSQ , {TRANWRD SEP }]
}
transition on STATEWRD to state [21]
  
-------------------
lalr_state [21]: {
  [Tran ::= TRANWRD ID STATEWRD (*) LSQ SNUM RSQ , {TRANWRD SEP }]
}
transition on LSQ to state [22]
  
-------------------
lalr_state [22]: {
  [Tran ::= TRANWRD ID STATEWRD LSQ (*) SNUM RSQ , {TRANWRD SEP }]
}
transition on SNUM to state [23]
  
-------------------
lalr_state [23]: {
  [Tran ::= TRANWRD ID STATEWRD LSQ SNUM (*) RSQ , {TRANWRD SEP }]
}
transition on RSQ to state [24]
  
-------------------
lalr_state [24]: {
  [Tran ::= TRANWRD ID STATEWRD LSQ SNUM RSQ (*) , {TRANWRD SEP }]
}
  
-------------------
lalr_state [25]: {
  [Trans ::= Tran Trans (*) , {SEP }]
}
  
-------------------
lalr_state [26]: {
  [Items ::= Item Items (*) , {RCUR }]
}
  
-------------------
lalr_state [27]: {
  [Item ::= LSQ ID (*) DERSYM SentForm COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
}
transition on DERSYM to state [28]
  
-------------------
lalr_state [28]: {
  [Ids ::= (*) ID Ids , {DOTSYM }]
  [SentForm ::= (*) Ids DOTSYM , {COMMA }]
  [SentForm ::= (*) DOTSYM , {COMMA }]
  [SentForm ::= (*) DOTSYM Ids , {COMMA }]
  [Item ::= LSQ ID DERSYM (*) SentForm COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
  [Ids ::= (*) ID , {DOTSYM }]
  [SentForm ::= (*) Ids DOTSYM Ids , {COMMA }]
}
transition on DOTSYM to state [32]
transition on SentForm to state [31]
transition on Ids to state [30]
transition on ID to state [29]
  
-------------------
lalr_state [29]: {
  [Ids ::= ID (*) Ids , {RCUR COMMA DOTSYM }]
  [Ids ::= (*) ID Ids , {RCUR COMMA DOTSYM }]
  [Ids ::= ID (*) , {RCUR COMMA DOTSYM }]
  [Ids ::= (*) ID , {RCUR COMMA DOTSYM }]
}
transition on Ids to state [41]
transition on ID to state [29]
  
-------------------
lalr_state [30]: {
  [SentForm ::= Ids (*) DOTSYM , {COMMA }]
  [SentForm ::= Ids (*) DOTSYM Ids , {COMMA }]
}
transition on DOTSYM to state [39]
  
-------------------
lalr_state [31]: {
  [Item ::= LSQ ID DERSYM SentForm (*) COMMA LCUR Ids RCUR RSQ , {LSQ RCUR }]
}
transition on COMMA to state [34]
  
-------------------
lalr_state [32]: {
  [Ids ::= (*) ID Ids , {COMMA }]
  [SentForm ::= DOTSYM (*) , {COMMA }]
  [SentForm ::= DOTSYM (*) Ids , {COMMA }]
  [Ids ::= (*) ID , {COMMA }]
}
transition on Ids to state [33]
transition on ID to state [29]
  
-------------------
lalr_state [33]: {
  [SentForm ::= DOTSYM Ids (*) , {COMMA }]
}
  
-------------------
lalr_state [34]: {
  [Item ::= LSQ ID DERSYM SentForm COMMA (*) LCUR Ids RCUR RSQ , {LSQ RCUR }]
}
transition on LCUR to state [35]
  
-------------------
lalr_state [35]: {
  [Ids ::= (*) ID Ids , {RCUR }]
  [Item ::= LSQ ID DERSYM SentForm COMMA LCUR (*) Ids RCUR RSQ , {LSQ RCUR }]
  [Ids ::= (*) ID , {RCUR }]
}
transition on Ids to state [36]
transition on ID to state [29]
  
-------------------
lalr_state [36]: {
  [Item ::= LSQ ID DERSYM SentForm COMMA LCUR Ids (*) RCUR RSQ , {LSQ RCUR }]
}
transition on RCUR to state [37]
  
-------------------
lalr_state [37]: {
  [Item ::= LSQ ID DERSYM SentForm COMMA LCUR Ids RCUR (*) RSQ , {LSQ RCUR }]
}
transition on RSQ to state [38]
  
-------------------
lalr_state [38]: {
  [Item ::= LSQ ID DERSYM SentForm COMMA LCUR Ids RCUR RSQ (*) , {LSQ RCUR }]
}
  
-------------------
lalr_state [39]: {
  [SentForm ::= Ids DOTSYM (*) , {COMMA }]
  [Ids ::= (*) ID Ids , {COMMA }]
  [SentForm ::= Ids DOTSYM (*) Ids , {COMMA }]
  [Ids ::= (*) ID , {COMMA }]
}
transition on Ids to state [40]
transition on ID to state [29]
  
-------------------
lalr_state [40]: {
  [SentForm ::= Ids DOTSYM Ids (*) , {COMMA }]
}
  
-------------------
lalr_state [41]: {
  [Ids ::= ID Ids (*) , {RCUR COMMA DOTSYM }]
}
  
-------------------
lalr_state [42]: {
  [States ::= State States (*) , {EOF }]
}
  
-------------------
lalr_state [43]: {
  [$START ::= StartState EOF (*) , {EOF }]
}
  
-------------------
""";
}
