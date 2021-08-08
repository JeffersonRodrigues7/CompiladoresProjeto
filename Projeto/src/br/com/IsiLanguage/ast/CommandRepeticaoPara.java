package br.com.IsiLanguage.ast;

import java.util.ArrayList;

public class CommandRepeticaoPara extends AbstractCommand {
	private String condition;
	private String varName;
	private String itera;
	private ArrayList<AbstractCommand> listaFor;
	
	public CommandRepeticaoPara(String vn, String condition, String it, ArrayList<AbstractCommand> lt) {
		this.condition = condition;
		this.listaFor = lt;
		this.varName = vn;
		this.itera = it;
	}
	
	@Override
	public String generateJavaCode() {
		// TODO Auto-generated method stub
		StringBuilder str = new StringBuilder();
		str.append("for (" + varName +";"+ condition + ";" + itera +"){\n");
		for (AbstractCommand cmd: listaFor) {
			str.append(cmd.generateJavaCode()+"\n");
		}
		str.append("}\n");
		return str.toString();
	}
	
	@Override
	public String toString() {
		return "CommandRepeticaoPara [varName=" + varName + ", condition=" + condition + ", itera=" + itera + ", listaFor=[" + listaFor + "]";
	}
	
	
}
