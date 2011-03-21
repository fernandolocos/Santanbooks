package trabalho.impl;

import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class Enquirer implements IEnquirer 
{
	private String animal;
	IObjetoConhecimento obj;
	
	public Enquirer(String animal)
	{
		this.animal = animal;
		IBaseConhecimento bc = new BaseConhecimento();
		obj = bc.recuperaObjeto(animal);
	}
	
	public void connect(IResponder responder) 
	{            
		boolean acertei = false;
	    IDeclaracao decl = obj.primeira();
	    
	    while(decl != null && responder.ask(decl.getPropriedade()).equalsIgnoreCase(decl.getValor()))
	    	decl = obj.proxima();
	    
	    if(decl == null)
	    	acertei = responder.finalAnswer(animal);
	    else
	    	acertei = responder.finalAnswer("nao conheceu");
	    
	    if(acertei)
	    	System.out.println("acertei");
	    else
	    	System.out.println("errei");
	    	
	    	
	}

}
