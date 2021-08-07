package br.com.IsiLanguage.ast;

import java.util.ArrayList;

public class CommandRepeticaoEnquanto extends AbstractCommand {
	private String condition;
	private ArrayList<AbstractCommand> listaWhile;
	
	public CommandRepeticaoEnquanto(String condition, ArrayList<AbstractCommand> lt) {
		this.condition = condition;
		this.listaWhile = lt;
	}
	
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append("while ("+condition+") {\n");
		for (AbstractCommand cmd: listaWhile) {
			str.append(cmd.generateJavaCode()+"\n");
		}
		str.append("}");
		return str.toString();
	}
	
	@Override
	public String toString() {
		return "CommandRepeticaoEnquanto [condition=" + condition + ", listaWhile=[" + listaWhile + "]";
	}
	
	
}
