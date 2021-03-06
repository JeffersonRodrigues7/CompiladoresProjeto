grammar IsiLang;

@header{
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
}

@members{
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
	private ArrayList<Integer> types = new ArrayList<Integer>();//Vari?vel que vai armazenar os tipos em uma linha de atribui??o
	
	public void verificaID(String id){
		if (!symbolTable.exists(id)){
			throw new IsiSemanticException("Symbol "+id+" not declared");
		}
	}
	
	public IsiSymbol getSymbol(String id){//Retorna o simbolo
		return symbolTable.get(id);
	}
	
	public int isNumeric(String str) {//Retorna 0 se for numero ou 1 se n?o for numero
		try {  
		    Double.parseDouble(str);  
		    return 0;
		} catch(NumberFormatException e){  
		    return 1;  
		}  
	}
	
	public void verifyTypes(){//Essa funcao verifica se dentro da arraylist todos os tipos sao iguais, utilizada para fazer verificar se a atribuicao esta correta
		for(int i = 0; i < types.size()-1; i++)//Vou percorrer a lista para verificar se h? alguma incompatibilidade de tipos
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
}

prog	: 'programa' decl bloco 'fimprog;'
           {  program.setVarTable(symbolTable);
           	  program.setComandos(stack.pop());
           	 
           } 
		;
		
decl    :  (declaravar)+
        ;
        
        
declaravar :  tipo ID  {
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
              (  VIR 
              	 ID {
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
              )* 
               SC
           ;
           
tipo       : 'numero' { _tipo = IsiVariable.NUMBER;  }
           | 'texto'  { _tipo = IsiVariable.TEXT;  }
           | 'boolean'  { _tipo = IsiVariable.BOOLEAN;  }
           ;
        
bloco	: { curThread = new ArrayList<AbstractCommand>(); 
	        stack.push(curThread);  
          }
          (cmd)+
		;
		

cmd		:  cmdleitura  
 		|  cmdescrita 
 		|  cmdattrib
 		|  cmdselecao  
 		|  cmdrepeticaoEnquanto
 		|  cmdrepeticaoFazer
 		|  cmdrepeticaoPara
		;
		
cmdleitura	: 'leia' AP
                     ID { content = _input.LT(-1).getText();
                     	  verificaID(content);
                     	  _readID = content;
                     	  
                     	  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
                    	  variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
                     	  
                        } 
                     FP 
                     SC 
                     
              {
              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
              	CommandLeitura cmd = new CommandLeitura(_readID, var);
              	stack.peek().add(cmd);
              }   
			;
			
cmdescrita	: 'escreva' 
                 AP 
                 ID { content = _input.LT(-1).getText();
                 	  verificaID(content);
	                  _writeID = content;
	                  
	                  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
                      variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
                     } 
                 FP 
                 SC
               {
               	  CommandEscrita cmd = new CommandEscrita(_writeID);
               	  stack.peek().add(cmd);
               }
			;
			
cmdattrib	:  ID { verificaID(_input.LT(-1).getText());
                    _exprID = _input.LT(-1).getText();
                    
                    IsiVariable variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
               	    int type = variable.getType();//Verificando o tipo da vari?vel que quero colocar algo
               	    types.add(type);
               } 
               ATTR { _exprContent = ""; } 
               expr 
               SC
               {
               	 verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
               	 CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
               	 stack.peek().add(cmd);
               }
			;
			
			
cmdselecao  :  'se' AP
                    ID    {content = _input.LT(-1).getText();
                    	   verificaID(content);
                    	   _exprDecision = content; 
                    	   
                    	  IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
                    	  variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
                    }
                    OPREL { _exprDecision += _input.LT(-1).getText(); }
                    (ID | NUMBER) { content = _input.LT(-1).getText();
                    	   		    _exprDecision += content;  
                    	   		   
                    	   		    if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele j? foi usado
										variable = (IsiVariable)getSymbol(content);//Pegando variavel
                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
								    }
                    	   		  }
                    FP 
                    ACH 
                    { curThread = new ArrayList<AbstractCommand>(); 
                      stack.push(curThread);
                    }
                    (cmd)+ 
                    
                    FCH 
                    {
                       listaTrue = stack.pop();	
                    } 
                   ('senao' 
                   	 ACH
                   	 {
                   	 	curThread = new ArrayList<AbstractCommand>();
                   	 	stack.push(curThread);
                   	 } 
                   	(cmd+) 
                   	FCH
                   	{
                   		listaFalse = stack.pop();
                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
                   		stack.peek().add(cmd);
                   	}
                   )?
            ;

cmdrepeticaoEnquanto	: 'enquanto' AP
							 ID {  content =  _input.LT(-1).getText();
							 		verificaID(content);
							 	   _exprWhile = content;
									
		                    	   IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    	   variable.setUsed();//Aqui estamos falando que a variavel foi utilizada  
		                    	}
							 OPREL { _exprWhile += _input.LT(-1).getText(); }
							 (ID | NUMBER) { content = _input.LT(-1).getText();
		                   				  	 _exprWhile += content;
		                    	   		     if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele j? foi usado
												variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
										     } 
		               		 }
							 FP
							 ACH
							 { curThread = new ArrayList<AbstractCommand>(); 
		                       stack.push(curThread);
		                     }
		                     (cmd)+ 
							 FCH{
							     listaWhile = stack.pop();
		                   		 CommandRepeticaoEnquanto cmd = new CommandRepeticaoEnquanto(_exprWhile, listaWhile);
		                   		 stack.peek().add(cmd);
                   			 }
                 ;
                 
cmdrepeticaoFazer :  'fazer' 
							 ACH
							 { curThread = new ArrayList<AbstractCommand>(); 
		                       stack.push(curThread);
		                     }
		                     (cmd)+ 
							 FCH
                   	 'enquanto'
                   			 AP
                   			 ID {  content =  _input.LT(-1).getText();
                   			 		verificaID(content);
							 	   _exprDo = content;
									
		                    	   IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    	   variable.setUsed();//Aqui estamos falando que a variavel foi utilizada  
		                    	}
							 OPREL { _exprDo += _input.LT(-1).getText(); }
							 (ID | NUMBER) { content = _input.LT(-1).getText();
		                   				  	 _exprDo += content;
		                    	   		     if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele j? foi usado
												variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    	  		    	variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
										     } 
		               		 }
		               		 FP{
							     listaDo = stack.pop();
		                   		 CommandRepeticaoFazer cmd = new CommandRepeticaoFazer(_exprDo, listaDo);
		                   		 stack.peek().add(cmd);
                   			 }
                   			 SC
                 ;
              
cmdrepeticaoPara	 :'para' 
					   AP
					    
					  ID { verificaID(_input.LT(-1).getText());
		                    _exprID = _input.LT(-1).getText();
		                    
		                    IsiVariable variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
		                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
		               	    int type = variable.getType();//Verificando o tipo da vari?vel que quero colocar algo
		               	    types.add(type);
		                } 
		               ATTR { _exprContent = _exprID + "="; } 
		              expr 
		              {
		              	 _First = _exprContent;
		               	  verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
		              }
					  SC
					  
					  ID { content =  _input.LT(-1).getText();
					  		_exprFor = _input.LT(-1).getText();
					  		
					  		variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada 
		              }
					  OPREL{_exprFor += _input.LT(-1).getText();}
					  (ID | NUMBER) {
				  						content = _input.LT(-1).getText();
	                   				  	_exprFor += content;
	                    	   		    if (symbolTable.exists(content)){//Caso tenhamos um ID vamos dizer que ele j? foi usado
											variable = (IsiVariable)getSymbol(content);//Pegando variavel
		                    	  		    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
		                    	  	    }
		              }
					  SC
					  ID { verificaID(_input.LT(-1).getText());
		                    _exprID = _input.LT(-1).getText();
		                    
		                    variable = (IsiVariable)getSymbol(_exprID);//Pegando variavel
		                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
		               	    type = variable.getType();//Verificando o tipo da vari?vel que quero colocar algo
		               	    types.add(type);
		                } 
		               ATTR { _exprContent = _exprID + "="; } 
		              expr 
		              {
		               	  verifyTypes();//Vou verificar se todos os valores da arrayList possuem o mesmo tipo
		              }
			                
					  FP 
					  
					  ACH {
					  		curThread = new ArrayList<AbstractCommand>(); 
		                    stack.push(curThread);
					  }
					  (cmd)+
					  FCH {
					  		listaFor = stack.pop();
	                   		CommandRepeticaoPara cmd = new CommandRepeticaoPara(_First, _exprFor, _exprContent, listaFor);
	                   		stack.peek().add(cmd);
	                   		
					  }
					  ;
	
expr		:  termo ( 
	             OP  { _exprContent += _input.LT(-1).getText();}
	            termo
	            )*
			;
			
termo		: ID {  content = _input.LT(-1).getText();
					verificaID(content);
	               _exprContent += content;
	               
	                IsiVariable variable = (IsiVariable)getSymbol(content);//Pegando variavel
                    variable.setUsed();//Aqui estamos falando que a variavel foi utilizada
                    
               	    int type = variable.getType();//Verificando o tipo da vari?vel que quero colocar algo
               	    types.add(type);
                 } 
            | 
              NUMBER
              {
              	content = _input.LT(-1).getText();
              	_exprContent += content;
              	type = isNumeric(content);
              	types.add(type);
              }
            | 
              TEXT
			  {
              	content = _input.LT(-1).getText();
              	_exprContent += content;
              	type = isNumeric(content);
              	types.add(type);
              }   
			;
			
	
AP	: '('
	;
	
FP	: ')'
	;
	
SC	: ';'
	;
	
OP	: '+' | '-' | '*' | '/'
	;
	
ATTR : '='
	 ;
	 
VIR  : ','
     ;
     
ACH  : '{'
     ;
     
FCH  : '}'
     ;
	 
	 
OPREL : '>' | '<' | '>=' | '<=' | '==' | '!='
      ;
      
ID	: [a-z] ([a-z] | [A-Z] | [0-9])*
	;
	
NUMBER	: [0-9]+ ('.' [0-9]+)?
		;
		
TEXT : '"'.*?'"'
 	 ;
 	 
BOOLEAN  : 'true' | 'false'
		 ;
		
WS	: (' ' | '\t' | '\n' | '\r') -> skip;

COMMENT : '//' ~[\r\n]* -> skip
		;