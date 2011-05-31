package pt.c01interfaces.s01chaveid.lgazire.impl;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class Responder implements IResponder
{
	private String animal;
	private IObjetoConhecimento obj;
	
	public Responder(String animal)
	{
		this.animal = animal;
		
		IBaseConhecimento bc = new BaseConhecimento();
		
		obj = bc.recuperaObjeto(animal);
	}
	
	public Responder()
	{
		
		IBaseConhecimento bc = new BaseConhecimento();
		
		obj = bc.recuperaObjeto(null);
	}
	
	@Override
	public String ask(String question)
	{
		String resp = "nao sei";
		
		IDeclaracao decl = obj.primeira();
		while (decl != null && !decl.getPropriedade().equalsIgnoreCase(question))
			decl = obj.proxima();
		
		if (decl != null)
			resp = decl.getValor();
		
		return resp;
	}

	@Override
	public boolean finalAnswer(String answer)
	{
        return answer.equalsIgnoreCase(animal);
	}

}
