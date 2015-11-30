// Generated from TinyJML.g4 by ANTLR 4.5.1
 package edu.iastate.cs.design.spec.parse; 
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TinyJMLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT_OPEN=1, COMMENT_CLOSE=2, JML_ANNOTATION=3, ENSURES=4, REQUIRES=5, 
		EQUAL=6, NUMBER=7, WS=8;
	public static final int
		RULE_tiny_specification = 0, RULE_jml_requires = 1, RULE_jml_ensures = 2, 
		RULE_expression = 3;
	public static final String[] ruleNames = {
		"tiny_specification", "jml_requires", "jml_ensures", "expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'/*'", "'*/'", "'@'", "'ensures'", "'requires'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "COMMENT_OPEN", "COMMENT_CLOSE", "JML_ANNOTATION", "ENSURES", "REQUIRES", 
		"EQUAL", "NUMBER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TinyJML.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TinyJMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Tiny_specificationContext extends ParserRuleContext {
		public TerminalNode COMMENT_OPEN() { return getToken(TinyJMLParser.COMMENT_OPEN, 0); }
		public List<TerminalNode> JML_ANNOTATION() { return getTokens(TinyJMLParser.JML_ANNOTATION); }
		public TerminalNode JML_ANNOTATION(int i) {
			return getToken(TinyJMLParser.JML_ANNOTATION, i);
		}
		public List<TerminalNode> WS() { return getTokens(TinyJMLParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(TinyJMLParser.WS, i);
		}
		public Jml_requiresContext jml_requires() {
			return getRuleContext(Jml_requiresContext.class,0);
		}
		public Jml_ensuresContext jml_ensures() {
			return getRuleContext(Jml_ensuresContext.class,0);
		}
		public TerminalNode COMMENT_CLOSE() { return getToken(TinyJMLParser.COMMENT_CLOSE, 0); }
		public Tiny_specificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tiny_specification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).enterTiny_specification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).exitTiny_specification(this);
		}
	}

	public final Tiny_specificationContext tiny_specification() throws RecognitionException {
		Tiny_specificationContext _localctx = new Tiny_specificationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_tiny_specification);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			match(COMMENT_OPEN);
			setState(9);
			match(JML_ANNOTATION);
			setState(10);
			match(WS);
			setState(11);
			jml_requires();
			setState(12);
			match(WS);
			setState(13);
			match(JML_ANNOTATION);
			setState(14);
			match(WS);
			setState(15);
			jml_ensures();
			setState(16);
			match(WS);
			setState(17);
			match(JML_ANNOTATION);
			setState(18);
			match(COMMENT_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Jml_requiresContext extends ParserRuleContext {
		public TerminalNode REQUIRES() { return getToken(TinyJMLParser.REQUIRES, 0); }
		public TerminalNode WS() { return getToken(TinyJMLParser.WS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Jml_requiresContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jml_requires; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).enterJml_requires(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).exitJml_requires(this);
		}
	}

	public final Jml_requiresContext jml_requires() throws RecognitionException {
		Jml_requiresContext _localctx = new Jml_requiresContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_jml_requires);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(REQUIRES);
			setState(21);
			match(WS);
			setState(22);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Jml_ensuresContext extends ParserRuleContext {
		public TerminalNode ENSURES() { return getToken(TinyJMLParser.ENSURES, 0); }
		public TerminalNode WS() { return getToken(TinyJMLParser.WS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Jml_ensuresContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jml_ensures; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).enterJml_ensures(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).exitJml_ensures(this);
		}
	}

	public final Jml_ensuresContext jml_ensures() throws RecognitionException {
		Jml_ensuresContext _localctx = new Jml_ensuresContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_jml_ensures);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(ENSURES);
			setState(25);
			match(WS);
			setState(26);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<TerminalNode> NUMBER() { return getTokens(TinyJMLParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(TinyJMLParser.NUMBER, i);
		}
		public TerminalNode EQUAL() { return getToken(TinyJMLParser.EQUAL, 0); }
		public List<TerminalNode> WS() { return getTokens(TinyJMLParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(TinyJMLParser.WS, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TinyJMLListener ) ((TinyJMLListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(NUMBER);
			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(29);
				match(WS);
				}
				}
				setState(34);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(35);
			match(EQUAL);
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(36);
				match(WS);
				}
				}
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(42);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\n/\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\7\5!\n\5\f\5\16\5$\13\5\3\5\3\5\7"+
		"\5(\n\5\f\5\16\5+\13\5\3\5\3\5\3\5\2\2\6\2\4\6\b\2\2,\2\n\3\2\2\2\4\26"+
		"\3\2\2\2\6\32\3\2\2\2\b\36\3\2\2\2\n\13\7\3\2\2\13\f\7\5\2\2\f\r\7\n\2"+
		"\2\r\16\5\4\3\2\16\17\7\n\2\2\17\20\7\5\2\2\20\21\7\n\2\2\21\22\5\6\4"+
		"\2\22\23\7\n\2\2\23\24\7\5\2\2\24\25\7\4\2\2\25\3\3\2\2\2\26\27\7\7\2"+
		"\2\27\30\7\n\2\2\30\31\5\b\5\2\31\5\3\2\2\2\32\33\7\6\2\2\33\34\7\n\2"+
		"\2\34\35\5\b\5\2\35\7\3\2\2\2\36\"\7\t\2\2\37!\7\n\2\2 \37\3\2\2\2!$\3"+
		"\2\2\2\" \3\2\2\2\"#\3\2\2\2#%\3\2\2\2$\"\3\2\2\2%)\7\b\2\2&(\7\n\2\2"+
		"\'&\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*,\3\2\2\2+)\3\2\2\2,-\7\t\2"+
		"\2-\t\3\2\2\2\4\")";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}