package main;
import java_cup.runtime.Symbol;

%%

%public
%unicode
%cup
%line
%column

%{

  private StringTable stringTable = new StringTable();
  public StringTable getStringTable(){
    return stringTable;
  }

  private Symbol symbol(int type) {
    return new Symbol(type);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, value);
  }

  private Symbol installID(int type, String key){
    stringTable.put(key);
    return symbol(type, stringTable.get(key));
  }

  private String errorMsg(String ch){
    return "'" + ch + "'" + "@" + yyline + ":" + yycolumn;
  }
%}


LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:][:jletterdigit:]*
Digit = [:digit:]
Digits = {Digit}+

%%

<YYINITIAL> {WhiteSpace}         { /* ignore */ }

/* keywords */
<YYINITIAL> {
  ^"START"                       { return symbol(sym.START); }
  "lalr_state"                   { return symbol(sym.LALRWRD); }
  "transition on"                { return symbol(sym.TRANWRD); }
  "to state"                     { return symbol(sym.STATEWRD); }

}

/* tokens */
<YYINITIAL> {
  {Digits}                       { return symbol(sym.SNUM, yytext()); }
  ":"                            { return symbol(sym.COLON); }
  "["                            { return symbol(sym.LSQ); }
  "]"                            { return symbol(sym.RSQ); }
  "{"                            { return symbol(sym.LCUR); }
  "}"                            { return symbol(sym.RCUR); }
  {Identifier}                   { return installID(sym.ID, yytext()); }
  "::="                          { return symbol(sym.DERSYM); }
  ","                            { return symbol(sym.COMMA); }
  "(*)"                          { return symbol(sym.DOTSYM); }
  "-------------------"          { return symbol(sym.SEP); }
}

/* error fallback */
[^]                              { return symbol(sym.error, errorMsg(yytext())); }
