// Generated from IsiLang.g4 by ANTLR 4.7.1
package br.com.IsiLanguage.parser;

	import br.com.IsiLanguage.datastructures.IsiSymbol;
	import br.com.IsiLanguage.datastructures.IsiVariable;
	import br.com.IsiLanguage.datastructures.IsiSymbolTable;
	import br.com.IsiLanguage.exceptions.IsiSemanticException;
	import br.com.IsiLanguage.ast.IsiProgram;
	import br.com.IsiLanguage.ast.AbstractCommand;
	import br.com.IsiLanguage.ast.CommandLeitura;
	import br.com.IsiLanguage.ast.CommandEscrita;
	import br.com.IsiLanguage.ast.CommandAtribuicao;
	import br.com.IsiLanguage.ast.CommandDecisao;
	import br.com.IsiLanguage.ast.CommandRepeticaoEnquanto;
	import br.com.IsiLanguage.ast.CommandRepeticaoFazer;
	import br.com.IsiLanguage.ast.CommandRepeticaoPara;
	import java.util.ArrayList;
	import java.util.Stack;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IsiLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, AP=13, FP=14, SC=15, OP=16, ATTR=17, VIR=18, 
		ACH=19, FCH=20, OPREL=21, ID=22, NUMBER=23, TEXT=24, BOOLEAN=25, WS=26, 
		COMMENT=27;
	public static final int
		RULE_prog = 0, RULE_decl = 1, RULE_declaravar = 2, RULE_tipo = 3, RULE_bloco = 4, 
		RULE_cmd = 5, RULE_cmdleitura = 6, RULE_cmdescrita = 7, RULE_cmdattrib = 8, 
		RULE_cmdselecao = 9, RULE_cmdrepeticaoEnquanto = 10, RULE_cmdrepeticaoFazer = 11, 
		RULE_cmdrepeticaoPara = 12, RULE_expr = 13, RULE_termo = 14;
	public static final String[] ruleNames = {
		"prog", "decl", "declaravar", "tipo", "bloco", "cmd", "cmdleitura", "cmdescrita", 
		"cmdattrib", "cmdselecao", "cmdrepeticaoEnquanto", "cmdrepeticaoFazer", 
		"cmdrepeticaoPara", "expr", "termo"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'programa'", "'fimprog;'", "'numero'", "'texto'", "'boolean'", 
		"'leia'", "'escreva'", "'se'", "'senao'", "'enquanto'", "'fazer'", "'para'", 
		"'('", "')'", "';'", null, "'='", "','", "'{'", "'}'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "AP", "FP", "SC", "OP", "ATTR", "VIR", "ACH", "FCH", "OPREL", "ID", 
		"NUMBER", "TEXT", "BOOLEAN", "WS", "COMMENT"
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
	public String getGrammarFileName() { return "IsiLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


		private int _tipo;
		private String _varName;
		private String _varValue;
		private IsiSymbolTable symbolTable = new IsiSymbolTable();
		private IsiSymbol symbol;
		private IsiProgram program = new IsiProgram();
		private ArrayList<AbstractCommand> curThread;
		private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
		private String _readID;
		private String _writeID;
		private String _exprID;
		private String _exprContent;
		private String _exprDecision;
		private String _exprWhile;
		private String _exprDo;
		private String _exprFor;
		private String _First;//Vou usar para armazenar a primeira parte do laco for
		private String content;//vai pegar o conteudo de uma atribuicao
		private int type;//vai armazenar o tipo de conteudo de uma atribuicao
		private ArrayList<AbstractCommand> listaTrue;
		private ArrayList<AbstractCommand> listaFalse;
		private ArrayList<AbstractCommand> listaWhile;
		private ArrayList<AbstractCommand> listaDo;
		private ArrayList<AbstractCommand> listaFor;
		private ArrayList<Integer> types = new ArrayList<Integer>();//Variável que vai armazenar os tipos em uma linha de atribuição
		
		public void verificaID(String id){
			if (!symbolTable.exists(id)){
				throw new IsiSemanticException("Symbol "+id+" not declared");
			}
		}
		
		public IsiSymbol getSymbol(String id){//Retorna o simbolo
			return symbolTable.get(id);
		}
		
		public int isNumeric(String str) {//Retorna 0 se for numero ou 1 se não for numero
			try {  
			    Double.parseDouble(str);  
			    return 0;
			} catch(NumberFormatException e){  
			    return 1;  
			}  
		}
		
		public void verifyTypes(){//Essa funcao verifica se dentro da arraylist todos os tipos sao iguais, utilizada para fazer verificar se a atribuicao esta correta
			for(int i = 0; i < types.size()-1; i++)//Vou percorrer a lista para verificar se há alguma incompatibilidade de tipos
			{
			    if(types.get(i)!=types.get(i+1)){
			    	throw new IsiSemanticException("Type mismatch");
			    }
			}
	 		types = new ArrayList<Integer>();
		}
		
		public void verificaUtilizacao(){
			System.out.println(symbolTable.getUsedInformation());
		}
		
		public void exibeComandos(){
			for (AbstractCommand c: program.getComandos()){
				System.out.println(c);
			}
		}
		
		public void generateCode(){
			program.generateTarget();
		}

	public IsiLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public BlocoContext bloco() {
			return getRuleContext(BlocoContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__0);
			setState(31);
			decl();
			setState(32);
			bloco();
			setState(33);
			match(T__1);
			  program.setVarTable(symbolTable);
			           	  program.setComandos(stack.pop());
			           	 
			           
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

	public static class DeclContext extends ParserRuleContext {
		public List<DeclaravarContext> declaravar() {
			return getRuleContexts(DeclaravarContext.class);
		}
		public DeclaravarContext declaravar(int i) {
			return getRuleContext(DeclaravarContext.class,i);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(36);
				declaravar();
				}
				}
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__4))) != 0) );
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

	public static class DeclaravarContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode SC() { return getToken(IsiLangParser.SC, 0); }
		public List<TerminalNode> VIR() { return getTokens(IsiLangParser.VIR); }
		public TerminalNode VIR(int i) {
			return getToken(IsiLangParser.VIR, i);
		}
		public DeclaravarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaravar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterDeclaravar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitDeclaravar(this);
		}
	}

	public final DeclaravarContext declaravar() throws RecognitionException {
		DeclaravarContext _localctx = new DeclaravarContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaravar);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			tipo();
			setState(42);
			match(ID);

				                  _varName = _input.LT(-1).getText();
				                  _varValue = null;
				                  symbol = new IsiVariable(_varName, _tipo, _varValue);
				                  if (!symbolTable.exists(_varName)){
				                     symbolTable.add(symbol);	
				                  }
				                  else{
				                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
				                  }
			                    
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VIR) {
				{
				{
				setState(44);
				match(VIR);
				setState(45);
				match(ID);

					                  _varName = _input.LT(-1).getText();
					                  _varValue = null;
					                  symbol = new IsiVariable(_varName, _tipo, _varValue);
					                  if (!symbolTable.exists(_varName)){
					                     symbolTable.add(symbol);	
					                  }
					                  else{
					                  	 throw new IsiSemanticException("Symbol "+_varName+" already declared");
					                  }
				                    
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(52);
			match(SC);
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

	public static class TipoContext extends ParserRuleContext {
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterTipo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitTipo(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tipo);
		try {
			setState(60);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				match(T__2);
				 _tipo = IsiVariable.NUMBER;  
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				match(T__3);
				 _tipo = IsiVariable.TEXT;  
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				match(T__4);
				 _tipo = IsiVariable.BOOLEAN;  
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class BlocoContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public BlocoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bloco; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterBloco(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitBloco(this);
		}
	}

	public final BlocoContext bloco() throws RecognitionException {
		BlocoContext _localctx = new BlocoContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_bloco);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 curThread = new ArrayList<AbstractCommand>(); 
				        stack.push(curThread);  
			          
			setState(64); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(63);
				cmd();
				}
				}
				setState(66); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
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

	public static class CmdContext extends ParserRuleContext {
		public CmdleituraContext cmdleitura() {
			return getRuleContext(CmdleituraContext.class,0);
		}
		public CmdescritaContext cmdescrita() {
			return getRuleContext(CmdescritaContext.class,0);
		}
		public CmdattribContext cmdattrib() {
			return getRuleContext(CmdattribContext.class,0);
		}
		public CmdselecaoContext cmdselecao() {
			return getRuleContext(CmdselecaoContext.class,0);
		}
		public CmdrepeticaoEnquantoContext cmdrepeticaoEnquanto() {
			return getRuleContext(CmdrepeticaoEnquantoContext.class,0);
		}
		public CmdrepeticaoFazerContext cmdrepeticaoFazer() {
			return getRuleContext(CmdrepeticaoFazerContext.class,0);
		}
		public CmdrepeticaoParaContext cmdrepeticaoPara() {
			return getRuleContext(CmdrepeticaoParaContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmd(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_cmd);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				cmdleitura();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				cmdescrita();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				cmdattrib();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 4);
				{
				setState(71);
				cmdselecao();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 5);
				{
				setState(72);
				cmdrepeticaoEnquanto();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 6);
				{
				setState(73);
				cmdrepeticaoFazer();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 7);
				{
				setState(74);
				cmdrepeticaoPara();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class CmdleituraContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode SC() { return getToken(IsiLangParser.SC, 0); }
		public CmdleituraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdleitura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdleitura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdleitura(this);
		}
	}

	public final CmdleituraContext cmdleitura() throws RecognitionException {
		CmdleituraContext _localctx = new CmdleituraContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_cmdleitura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(T__5);
			setState(78);
			match(AP);
			setState(79);
			match(ID);
			 content = _input.LT(-1).getText();
			                     	  verificaID(content);
			                     	  _readID = content;
			                     	  
			                     	  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
			                    	  variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
			                     	  
			                        
			setState(81);
			match(FP);
			setState(82);
			match(SC);

			              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
			              	CommandLeitura cmd = new CommandLeitura(_readID, var);
			              	stack.peek().add(cmd);
			              
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

	public static class CmdescritaContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode SC() { return getToken(IsiLangParser.SC, 0); }
		public CmdescritaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdescrita; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdescrita(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdescrita(this);
		}
	}

	public final CmdescritaContext cmdescrita() throws RecognitionException {
		CmdescritaContext _localctx = new CmdescritaContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cmdescrita);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(T__6);
			setState(86);
			match(AP);
			setState(87);
			match(ID);
			 content = _input.LT(-1).getText();
			                 	  verificaID(content);
				                  _writeID = content;
				                  
				                  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
			                      variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
			                     
			setState(89);
			match(FP);
			setState(90);
			match(SC);

			               	  CommandEscrita cmd = new CommandEscrita(_writeID);
			               	  stack.peek().add(cmd);
			               
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

	public static class CmdattribContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode ATTR() { return getToken(IsiLangParser.ATTR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SC() { return getToken(IsiLangParser.SC, 0); }
		public CmdattribContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdattrib; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdattrib(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdattrib(this);
		}
	}

	public final CmdattribContext cmdattrib() throws RecognitionException {
		CmdattribContext _localctx = new CmdattribContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cmdattrib);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(ID);
			 verificaID(_input.LT(-1).getText());
			                    _exprID = _input.LT(-1).getText();
			                    
			                    IsiVariable variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
			                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
			               	    int type = variable.getType();//Verificando o tipo da variável que quero colocar algo
			               	    types.add(type);
			               
			setState(95);
			match(ATTR);
			 _exprContent = ""; 
			setState(97);
			expr();
			setState(98);
			match(SC);

			               	 verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
			               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
			               	 stack.peek().add(cmd);
			               
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

	public static class CmdselecaoContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public List<TerminalNode> ACH() { return getTokens(IsiLangParser.ACH); }
		public TerminalNode ACH(int i) {
			return getToken(IsiLangParser.ACH, i);
		}
		public List<TerminalNode> FCH() { return getTokens(IsiLangParser.FCH); }
		public TerminalNode FCH(int i) {
			return getToken(IsiLangParser.FCH, i);
		}
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdselecaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdselecao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdselecao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdselecao(this);
		}
	}

	public final CmdselecaoContext cmdselecao() throws RecognitionException {
		CmdselecaoContext _localctx = new CmdselecaoContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_cmdselecao);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(T__7);
			setState(102);
			match(AP);
			setState(103);
			match(ID);
			content = _input.LT(-1).getText();
			                    	   verificaID(content);
			                    	   _exprDecision = content; 
			                    	   
			                    	  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
			                    	  variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
			                    
			setState(105);
			match(OPREL);
			 _exprDecision += _input.LT(-1).getText(); 
			setState(107);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			 content = _input.LT(-1).getText();
			                    	   		    _exprDecision += content;  
			                    	   		   
			                    	   		    if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele já foi usado
													variable = (IsiVariable)getSymbol(content);//Pegando variavel
			                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
											    }
			                    	   		  
			setState(109);
			match(FP);
			setState(110);
			match(ACH);
			 curThread = new ArrayList<AbstractCommand>(); 
			                      stack.push(curThread);
			                    
			setState(113); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(112);
				cmd();
				}
				}
				setState(115); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
			setState(117);
			match(FCH);

			                       listaTrue = stack.pop();	
			                    
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(119);
				match(T__8);
				setState(120);
				match(ACH);

				                   	 	curThread = new ArrayList<AbstractCommand>();
				                   	 	stack.push(curThread);
				                   	 
				{
				setState(123); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(122);
					cmd();
					}
					}
					setState(125); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
				}
				setState(127);
				match(FCH);

				                   		listaFalse = stack.pop();
				                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
				                   		stack.peek().add(cmd);
				                   	
				}
			}

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

	public static class CmdrepeticaoEnquantoContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode ACH() { return getToken(IsiLangParser.ACH, 0); }
		public TerminalNode FCH() { return getToken(IsiLangParser.FCH, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdrepeticaoEnquantoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdrepeticaoEnquanto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdrepeticaoEnquanto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdrepeticaoEnquanto(this);
		}
	}

	public final CmdrepeticaoEnquantoContext cmdrepeticaoEnquanto() throws RecognitionException {
		CmdrepeticaoEnquantoContext _localctx = new CmdrepeticaoEnquantoContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_cmdrepeticaoEnquanto);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(T__9);
			setState(133);
			match(AP);
			setState(134);
			match(ID);
			  content =  _input.LT(-1).getText();
										 		verificaID(content);
										 	   _exprWhile = content;
												
					                    	   IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    	   variable.setUsed();//Aqui estamos falando que a variavel foi utilizada  
					                    	
			setState(136);
			match(OPREL);
			 _exprWhile += _input.LT(-1).getText(); 
			setState(138);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			 content = _input.LT(-1).getText();
					                   				  	 _exprWhile += content;
					                    	   		     if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele já foi usado
															variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
													     } 
					               		 
			setState(140);
			match(FP);
			setState(141);
			match(ACH);
			 curThread = new ArrayList<AbstractCommand>(); 
					                       stack.push(curThread);
					                     
			setState(144); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(143);
				cmd();
				}
				}
				setState(146); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
			setState(148);
			match(FCH);

										     listaWhile = stack.pop();
					                   		 CommandRepeticaoEnquanto cmd = new CommandRepeticaoEnquanto(_exprWhile, listaWhile);
					                   		 stack.peek().add(cmd);
			                   			 
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

	public static class CmdrepeticaoFazerContext extends ParserRuleContext {
		public TerminalNode ACH() { return getToken(IsiLangParser.ACH, 0); }
		public TerminalNode FCH() { return getToken(IsiLangParser.FCH, 0); }
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode SC() { return getToken(IsiLangParser.SC, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdrepeticaoFazerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdrepeticaoFazer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdrepeticaoFazer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdrepeticaoFazer(this);
		}
	}

	public final CmdrepeticaoFazerContext cmdrepeticaoFazer() throws RecognitionException {
		CmdrepeticaoFazerContext _localctx = new CmdrepeticaoFazerContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_cmdrepeticaoFazer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			match(T__10);
			setState(152);
			match(ACH);
			 curThread = new ArrayList<AbstractCommand>(); 
					                       stack.push(curThread);
					                     
			setState(155); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(154);
				cmd();
				}
				}
				setState(157); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
			setState(159);
			match(FCH);
			setState(160);
			match(T__9);
			setState(161);
			match(AP);
			setState(162);
			match(ID);
			  content =  _input.LT(-1).getText();
			                   			 		verificaID(content);
										 	   _exprDo = content;
												
					                    	   IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    	   variable.setUsed();//Aqui estamos falando que a variavel foi utilizada  
					                    	
			setState(164);
			match(OPREL);
			 _exprDo += _input.LT(-1).getText(); 
			setState(166);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			 content = _input.LT(-1).getText();
					                   				  	 _exprDo += content;
					                    	   		     if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele já foi usado
															variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
													     } 
					               		 
			setState(168);
			match(FP);

										     listaDo = stack.pop();
					                   		 CommandRepeticaoFazer cmd = new CommandRepeticaoFazer(_exprDo, listaDo);
					                   		 stack.peek().add(cmd);
			                   			 
			setState(170);
			match(SC);
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

	public static class CmdrepeticaoParaContext extends ParserRuleContext {
		public TerminalNode AP() { return getToken(IsiLangParser.AP, 0); }
		public List<TerminalNode> ID() { return getTokens(IsiLangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(IsiLangParser.ID, i);
		}
		public List<TerminalNode> ATTR() { return getTokens(IsiLangParser.ATTR); }
		public TerminalNode ATTR(int i) {
			return getToken(IsiLangParser.ATTR, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> SC() { return getTokens(IsiLangParser.SC); }
		public TerminalNode SC(int i) {
			return getToken(IsiLangParser.SC, i);
		}
		public TerminalNode OPREL() { return getToken(IsiLangParser.OPREL, 0); }
		public TerminalNode FP() { return getToken(IsiLangParser.FP, 0); }
		public TerminalNode ACH() { return getToken(IsiLangParser.ACH, 0); }
		public TerminalNode FCH() { return getToken(IsiLangParser.FCH, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public CmdrepeticaoParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdrepeticaoPara; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterCmdrepeticaoPara(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitCmdrepeticaoPara(this);
		}
	}

	public final CmdrepeticaoParaContext cmdrepeticaoPara() throws RecognitionException {
		CmdrepeticaoParaContext _localctx = new CmdrepeticaoParaContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cmdrepeticaoPara);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(T__11);
			setState(173);
			match(AP);
			setState(174);
			match(ID);
			 verificaID(_input.LT(-1).getText());
					                    _exprID = _input.LT(-1).getText();
					                    
					                    IsiVariable variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
					                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
					               	    int type = variable.getType();//Verificando o tipo da variável que quero colocar algo
					               	    types.add(type);
					                
			setState(176);
			match(ATTR);
			 _exprContent = _exprID + "="; 
			setState(178);
			expr();

					              	 _First = _exprContent;
					               	  verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
					              
			setState(180);
			match(SC);
			setState(181);
			match(ID);
			 content =  _input.LT(-1).getText();
								  		_exprFor = _input.LT(-1).getText();
								  		
								  		variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada 
					              
			setState(183);
			match(OPREL);
			_exprFor += _input.LT(-1).getText();
			setState(185);
			_la = _input.LA(1);
			if ( !(_la==ID || _la==NUMBER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}

							  						content = _input.LT(-1).getText();
				                   				  	_exprFor += content;
				                    	   		    if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele já foi usado
														variable = (IsiVariable)getSymbol(content);//Pegando variavel
					                    	  		    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
					                    	  	    }
					              
			setState(187);
			match(SC);
			setState(188);
			match(ID);
			 verificaID(_input.LT(-1).getText());
					                    _exprID = _input.LT(-1).getText();
					                    
					                    variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
					                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
					               	    type = variable.getType();//Verificando o tipo da variável que quero colocar algo
					               	    types.add(type);
					                
			setState(190);
			match(ATTR);
			 _exprContent = _exprID + "="; 
			setState(192);
			expr();

					               	  verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
					              
			setState(194);
			match(FP);
			setState(195);
			match(ACH);

								  		curThread = new ArrayList<AbstractCommand>(); 
					                    stack.push(curThread);
								  
			setState(198); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(197);
				cmd();
				}
				}
				setState(200); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << ID))) != 0) );
			setState(202);
			match(FCH);

								  		listaFor = stack.pop();
				                   		CommandRepeticaoPara cmd = new CommandRepeticaoPara(_First, _exprFor, _exprContent, listaFor);
				                   		stack.peek().add(cmd);
				                   		
								  
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

	public static class ExprContext extends ParserRuleContext {
		public List<TermoContext> termo() {
			return getRuleContexts(TermoContext.class);
		}
		public TermoContext termo(int i) {
			return getRuleContext(TermoContext.class,i);
		}
		public List<TerminalNode> OP() { return getTokens(IsiLangParser.OP); }
		public TerminalNode OP(int i) {
			return getToken(IsiLangParser.OP, i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			termo();
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP) {
				{
				{
				setState(206);
				match(OP);
				 _exprContent += _input.LT(-1).getText();
				setState(208);
				termo();
				}
				}
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class TermoContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(IsiLangParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(IsiLangParser.NUMBER, 0); }
		public TerminalNode TEXT() { return getToken(IsiLangParser.TEXT, 0); }
		public TermoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).enterTermo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IsiLangListener ) ((IsiLangListener)listener).exitTermo(this);
		}
	}

	public final TermoContext termo() throws RecognitionException {
		TermoContext _localctx = new TermoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_termo);
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				match(ID);
				  content = _input.LT(-1).getText();
									verificaID(content);
					               _exprContent += content;
					               
					                IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
				                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
				                    
				               	    int type = variable.getType();//Verificando o tipo da variável que quero colocar algo
				               	    types.add(type);
				                 
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(216);
				match(NUMBER);

				              	content = _input.LT(-1).getText();
				              	_exprContent += content;
				              	type = isNumeric(content);
				              	types.add(type);
				              
				}
				break;
			case TEXT:
				enterOuterAlt(_localctx, 3);
				{
				setState(218);
				match(TEXT);

				              	content = _input.LT(-1).getText();
				              	_exprContent += content;
				              	type = isNumeric(content);
				              	types.add(type);
				              
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u00e1\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\3\6\3(\n\3\r\3\16\3)\3\4\3\4\3\4\3\4\3\4\3\4\7\4\62\n\4\f\4\16"+
		"\4\65\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5?\n\5\3\6\3\6\6\6C\n\6\r"+
		"\6\16\6D\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7N\n\7\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\6\13t\n"+
		"\13\r\13\16\13u\3\13\3\13\3\13\3\13\3\13\3\13\6\13~\n\13\r\13\16\13\177"+
		"\3\13\3\13\3\13\5\13\u0085\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\6\f\u0093\n\f\r\f\16\f\u0094\3\f\3\f\3\f\3\r\3\r\3\r\3\r\6"+
		"\r\u009e\n\r\r\r\16\r\u009f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\6\16\u00c9\n\16\r\16\16\16\u00ca\3\16\3\16\3\16\3\17\3\17\3\17\3\17\7"+
		"\17\u00d4\n\17\f\17\16\17\u00d7\13\17\3\20\3\20\3\20\3\20\3\20\3\20\5"+
		"\20\u00df\n\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\3"+
		"\3\2\30\31\2\u00e5\2 \3\2\2\2\4\'\3\2\2\2\6+\3\2\2\2\b>\3\2\2\2\n@\3\2"+
		"\2\2\fM\3\2\2\2\16O\3\2\2\2\20W\3\2\2\2\22_\3\2\2\2\24g\3\2\2\2\26\u0086"+
		"\3\2\2\2\30\u0099\3\2\2\2\32\u00ae\3\2\2\2\34\u00cf\3\2\2\2\36\u00de\3"+
		"\2\2\2 !\7\3\2\2!\"\5\4\3\2\"#\5\n\6\2#$\7\4\2\2$%\b\2\1\2%\3\3\2\2\2"+
		"&(\5\6\4\2\'&\3\2\2\2()\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\5\3\2\2\2+,\5\b"+
		"\5\2,-\7\30\2\2-\63\b\4\1\2./\7\24\2\2/\60\7\30\2\2\60\62\b\4\1\2\61."+
		"\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\63"+
		"\3\2\2\2\66\67\7\21\2\2\67\7\3\2\2\289\7\5\2\29?\b\5\1\2:;\7\6\2\2;?\b"+
		"\5\1\2<=\7\7\2\2=?\b\5\1\2>8\3\2\2\2>:\3\2\2\2><\3\2\2\2?\t\3\2\2\2@B"+
		"\b\6\1\2AC\5\f\7\2BA\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2E\13\3\2\2\2"+
		"FN\5\16\b\2GN\5\20\t\2HN\5\22\n\2IN\5\24\13\2JN\5\26\f\2KN\5\30\r\2LN"+
		"\5\32\16\2MF\3\2\2\2MG\3\2\2\2MH\3\2\2\2MI\3\2\2\2MJ\3\2\2\2MK\3\2\2\2"+
		"ML\3\2\2\2N\r\3\2\2\2OP\7\b\2\2PQ\7\17\2\2QR\7\30\2\2RS\b\b\1\2ST\7\20"+
		"\2\2TU\7\21\2\2UV\b\b\1\2V\17\3\2\2\2WX\7\t\2\2XY\7\17\2\2YZ\7\30\2\2"+
		"Z[\b\t\1\2[\\\7\20\2\2\\]\7\21\2\2]^\b\t\1\2^\21\3\2\2\2_`\7\30\2\2`a"+
		"\b\n\1\2ab\7\23\2\2bc\b\n\1\2cd\5\34\17\2de\7\21\2\2ef\b\n\1\2f\23\3\2"+
		"\2\2gh\7\n\2\2hi\7\17\2\2ij\7\30\2\2jk\b\13\1\2kl\7\27\2\2lm\b\13\1\2"+
		"mn\t\2\2\2no\b\13\1\2op\7\20\2\2pq\7\25\2\2qs\b\13\1\2rt\5\f\7\2sr\3\2"+
		"\2\2tu\3\2\2\2us\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\7\26\2\2x\u0084\b\13\1"+
		"\2yz\7\13\2\2z{\7\25\2\2{}\b\13\1\2|~\5\f\7\2}|\3\2\2\2~\177\3\2\2\2\177"+
		"}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\7\26\2\2\u0082"+
		"\u0083\b\13\1\2\u0083\u0085\3\2\2\2\u0084y\3\2\2\2\u0084\u0085\3\2\2\2"+
		"\u0085\25\3\2\2\2\u0086\u0087\7\f\2\2\u0087\u0088\7\17\2\2\u0088\u0089"+
		"\7\30\2\2\u0089\u008a\b\f\1\2\u008a\u008b\7\27\2\2\u008b\u008c\b\f\1\2"+
		"\u008c\u008d\t\2\2\2\u008d\u008e\b\f\1\2\u008e\u008f\7\20\2\2\u008f\u0090"+
		"\7\25\2\2\u0090\u0092\b\f\1\2\u0091\u0093\5\f\7\2\u0092\u0091\3\2\2\2"+
		"\u0093\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096"+
		"\3\2\2\2\u0096\u0097\7\26\2\2\u0097\u0098\b\f\1\2\u0098\27\3\2\2\2\u0099"+
		"\u009a\7\r\2\2\u009a\u009b\7\25\2\2\u009b\u009d\b\r\1\2\u009c\u009e\5"+
		"\f\7\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\7\26\2\2\u00a2\u00a3\7"+
		"\f\2\2\u00a3\u00a4\7\17\2\2\u00a4\u00a5\7\30\2\2\u00a5\u00a6\b\r\1\2\u00a6"+
		"\u00a7\7\27\2\2\u00a7\u00a8\b\r\1\2\u00a8\u00a9\t\2\2\2\u00a9\u00aa\b"+
		"\r\1\2\u00aa\u00ab\7\20\2\2\u00ab\u00ac\b\r\1\2\u00ac\u00ad\7\21\2\2\u00ad"+
		"\31\3\2\2\2\u00ae\u00af\7\16\2\2\u00af\u00b0\7\17\2\2\u00b0\u00b1\7\30"+
		"\2\2\u00b1\u00b2\b\16\1\2\u00b2\u00b3\7\23\2\2\u00b3\u00b4\b\16\1\2\u00b4"+
		"\u00b5\5\34\17\2\u00b5\u00b6\b\16\1\2\u00b6\u00b7\7\21\2\2\u00b7\u00b8"+
		"\7\30\2\2\u00b8\u00b9\b\16\1\2\u00b9\u00ba\7\27\2\2\u00ba\u00bb\b\16\1"+
		"\2\u00bb\u00bc\t\2\2\2\u00bc\u00bd\b\16\1\2\u00bd\u00be\7\21\2\2\u00be"+
		"\u00bf\7\30\2\2\u00bf\u00c0\b\16\1\2\u00c0\u00c1\7\23\2\2\u00c1\u00c2"+
		"\b\16\1\2\u00c2\u00c3\5\34\17\2\u00c3\u00c4\b\16\1\2\u00c4\u00c5\7\20"+
		"\2\2\u00c5\u00c6\7\25\2\2\u00c6\u00c8\b\16\1\2\u00c7\u00c9\5\f\7\2\u00c8"+
		"\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2"+
		"\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\7\26\2\2\u00cd\u00ce\b\16\1\2\u00ce"+
		"\33\3\2\2\2\u00cf\u00d5\5\36\20\2\u00d0\u00d1\7\22\2\2\u00d1\u00d2\b\17"+
		"\1\2\u00d2\u00d4\5\36\20\2\u00d3\u00d0\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5"+
		"\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\35\3\2\2\2\u00d7\u00d5\3\2\2"+
		"\2\u00d8\u00d9\7\30\2\2\u00d9\u00df\b\20\1\2\u00da\u00db\7\31\2\2\u00db"+
		"\u00df\b\20\1\2\u00dc\u00dd\7\32\2\2\u00dd\u00df\b\20\1\2\u00de\u00d8"+
		"\3\2\2\2\u00de\u00da\3\2\2\2\u00de\u00dc\3\2\2\2\u00df\37\3\2\2\2\17)"+
		"\63>DMu\177\u0084\u0094\u009f\u00ca\u00d5\u00de";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}