package edu.iastate.cs.design.spec.parse;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ParseTest {

    public static void main(String[] args) {
        String testJml = "/*@ requires 5 = 6\n" +
                         "  @ ensures 6 = 5\n" +
                         "  @*/";
        TinyJMLLexer lexer = new TinyJMLLexer(new ANTLRInputStream(testJml));
        TokenStream tokenStream = new CommonTokenStream(lexer);
        TinyJMLParser parser = new TinyJMLParser(tokenStream);
        TinyJMLParser.Tiny_specificationContext context = parser.tiny_specification();
        ParseTreeWalker treeWalker = new ParseTreeWalker();
        CustomTinyJMLListener customListener = new CustomTinyJMLListener();
        treeWalker.walk(customListener, context);
    }
}
