package br.com.IsiLanguage.datastructures;

public class IsiVariable extends IsiSymbol {
	
	public static final int NUMBER=0;
	public static final int TEXT  =1;
	public static final int BOOLEAN  =2;
	
	private int type;
	private String value;
	private boolean used;//Vai dizer se a vari?vel foi usada ou n?o
	
	public IsiVariable(String name, int type, String value) {
		super(name);
		this.type = type;
		this.value = value;
		this.used = false;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setUsed() {
		this.used = true;
	}
	
	public boolean getUsed() {
		return this.used;
	}

	@Override
	public String toString() {
		return "IsiVariable [name=" + name + ", type=" + type + ", value=" + value + ", used=" + used +"]";
	}
	
	public String generateJavaCode() {
       String str;
       if (type == NUMBER) {
    	   str = "double ";
       }
       else if(type == TEXT){
    	   str = "String ";
       }else {
    	   str = "boolean";
       }
       return str + " "+super.name+";";
	}
	
	

}
