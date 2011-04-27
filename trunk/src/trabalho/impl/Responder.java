package trabalho.impl;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;


public class Responder implements IResponder 
{
	private String animal;
	IObjetoConhecimento obj;
	private Vector<String> propriedade;
	
	public Responder(String animal)
	{
		this.animal = animal;
		IBaseConhecimento bc = new BaseConhecimento();
		obj = bc.recuperaObjeto(animal);
		propriedade = new Vector<String>();
	}

	public String ask(String question) 
	{    
		 propriedade.add(question);
	     IDeclaracao decl = obj.primeira();    
	     String resp = "nao sei";
	     
	     while (decl != null && resp.equalsIgnoreCase("nao sei")) 
	     {
	    	 if(decl.getPropriedade().equalsIgnoreCase(question))resp = decl.getValor();
	    	 decl = obj.proxima();
	     }
	     propriedade.add(resp);
	     return resp;
	}
	
	public boolean finalAnswer (String answer)
	{
		System.out.println(answer);
		//System.out.println(propriedade.toString());
		return answer.equalsIgnoreCase(animal);
	}

}
