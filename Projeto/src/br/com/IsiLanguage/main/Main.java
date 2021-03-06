package br.com.IsiLanguage.main;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import br.com.IsiLanguage.exceptions.IsiSemanticException;
import br.com.IsiLanguage.parser.IsiLangLexer;
import br.com.IsiLanguage.parser.IsiLangParser;

// Aqui vamos instanciar o parser e apontar para o arquivo fonte extensao.isi
 
public class Main {

	public static void main(String[] args) {
		try {
			IsiLangLexer lexer;//analisador l?xico
			IsiLangParser parser;
			
			// l? o arquivo "input.isi" e isso ? entrada para o Analisador Lexico
			lexer = new IsiLangLexer(CharStreams.fromFileName("input.isi"));//Teste para testar casos sem erros
			//lexer = new IsiLangLexer(CharStreams.fromFileName("input2.isi"));//Erro na incompatibilidade de tipos
			//lexer = new IsiLangLexer(CharStreams.fromFileName("input3.isi"));//Vari?vel n?o declarada
			
			// cria-se o "fluxo de tokens" para passar para o PARSER
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			
			// criando o parser a partir do tokenStream
			parser = new IsiLangParser(tokenStream);
			
			parser.prog();
			
			System.out.println("Compilation Successful");
			
			parser.exibeComandos();
			
			parser.verificaUtilizacao();//Printando utilizacao ou nao das variaveis
			
			parser.generateCode();
			
		}
		catch(IsiSemanticException ex) {
			System.err.println("Erro semantico: "+ex.getMessage());
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("Erro: "+ex.getMessage());
		}
	}

}
