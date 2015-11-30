// Generated from TinyJML.g4 by ANTLR 4.5.1
 package edu.iastate.cs.design.spec.parse; 
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TinyJMLParser}.
 */
public interface TinyJMLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TinyJMLParser#tiny_specification}.
	 * @param ctx the parse tree
	 */
	void enterTiny_specification(TinyJMLParser.Tiny_specificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyJMLParser#tiny_specification}.
	 * @param ctx the parse tree
	 */
	void exitTiny_specification(TinyJMLParser.Tiny_specificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyJMLParser#jml_requires}.
	 * @param ctx the parse tree
	 */
	void enterJml_requires(TinyJMLParser.Jml_requiresContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyJMLParser#jml_requires}.
	 * @param ctx the parse tree
	 */
	void exitJml_requires(TinyJMLParser.Jml_requiresContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyJMLParser#jml_ensures}.
	 * @param ctx the parse tree
	 */
	void enterJml_ensures(TinyJMLParser.Jml_ensuresContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyJMLParser#jml_ensures}.
	 * @param ctx the parse tree
	 */
	void exitJml_ensures(TinyJMLParser.Jml_ensuresContext ctx);
	/**
	 * Enter a parse tree produced by {@link TinyJMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(TinyJMLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TinyJMLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(TinyJMLParser.ExpressionContext ctx);
}