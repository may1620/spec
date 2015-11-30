package edu.iastate.cs.design.spec.parse;

import org.antlr.v4.runtime.tree.TerminalNode;

public class CustomTinyJMLListener extends TinyJMLBaseListener {

    @Override
    public void enterJml_requires(TinyJMLParser.Jml_requiresContext ctx) {
        TinyJMLParser.ExpressionContext expression = ctx.expression();
        System.out.println("Entering requires node");
        for (TerminalNode node : expression.NUMBER()) {
            System.out.println(node.getText());
        }
    }

    @Override
    public void enterJml_ensures(TinyJMLParser.Jml_ensuresContext ctx) {
        TinyJMLParser.ExpressionContext expression = ctx.expression();
        System.out.println("Entering ensures node");
        for (TerminalNode node : expression.NUMBER()) {
            System.out.println(node.getText());
        }
    }
}
