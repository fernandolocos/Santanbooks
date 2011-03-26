package trabalho.impl;

import java.util.Vector;

import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class Responder implements IResponder 
{
	private String animal;
	IObjetoConhecimento obj;
	private Vector<String> v;
	
	public Responder(String animal)
	{
		this.animal = animal;
		IBaseConhecimento bc = new BaseConhecimento();
		obj = bc.recuperaObjeto(animal);
		v = new Vector<String>();
	}

	public String ask(String question) 
	{    
		 v.add(question);
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
		System.out.println(v.toString());
		return answer.equalsIgnoreCase(animal);
	}

}
