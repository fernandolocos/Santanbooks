package trabalho.impl;

import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class Responder implements IResponder 
{
	private String animal;
	IObjetoConhecimento obj;
	
	public Responder(String animal)
	{
		this.animal = animal;
		IBaseConhecimento bc = new BaseConhecimento();
		obj = bc.recuperaObjeto(animal);
	}

	public String ask(String question) 
	{    
	     IDeclaracao decl = obj.primeira();    
	     String resp = "nao sei";
	     
	     while (decl != null && resp.equalsIgnoreCase("nao sei")) 
	     {
	    	 if(decl.getPropriedade().equalsIgnoreCase(question))resp = decl.getValor();
	    	 decl = obj.proxima();
	     }
	     return resp;
	}
	
	public boolean finalAnswer (String answer)
	{
		return  answer.equalsIgnoreCase(animal);
	}

}
