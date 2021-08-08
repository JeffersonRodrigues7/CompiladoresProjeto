package br.com.IsiLanguage.ast;

import java.util.ArrayList;

public class CommandRepeticaoFazer extends AbstractCommand {
	private String condition;
	private ArrayList<AbstractCommand> listaDo;
	
	public CommandRepeticaoFazer(String condition, ArrayList<AbstractCommand> lt) {
		this.condition = condition;
		this.listaDo = lt;
	}
	
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append("do {\n");
		for (AbstractCommand cmd: listaDo) {
			str.append(cmd.generateJavaCode()+"\n");
		}
		str.append("}while("+condition+");\n");
		return str.toString();
	}
	
	@Override
	public String toString() {
		return "CommandRepeticaoFazer [condition=" + condition + ", listaDo=[" + listaDo + "]";
	}
	
	
}
