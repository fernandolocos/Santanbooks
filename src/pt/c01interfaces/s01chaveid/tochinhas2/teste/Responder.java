package pt.c01interfaces.s01chaveid.tochinhas2.teste;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class Responder implements IResponder
{
	private String animal;
	private IObjetoConhecimento obj;
	private String finalAnswer;
	
	public Responder(String animal)
	{
		this.animal = animal;
		
		IBaseConhecimento bc = new BaseConhecimento();
		
		obj = bc.recuperaObjeto(animal);
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
		finalAnswer = answer;
        return answer.equalsIgnoreCase(animal);
	}
	
	public boolean getFinalAnswer() {
        return finalAnswer.equalsIgnoreCase(animal);
	}
}
