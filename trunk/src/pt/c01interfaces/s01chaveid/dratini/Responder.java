package pt.c01interfaces.s01chaveid.dratini;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class Responder implements IResponder{
	
	private String animal;
	private BaseConhecimento baseC = new BaseConhecimento();
	private IObjetoConhecimento obj;
	public boolean lastAnswer;
	
	public void carregarAnimal(String animal){
		this.animal = animal;
		obj = baseC.recuperaObjeto(animal);
	}

	@Override
	public String ask(String question) {
		IDeclaracao declara = obj.primeira();
		while (declara != null){
			if (declara.getPropriedade().equalsIgnoreCase(question)){
				return declara.getValor();
			}
			declara = obj.proxima();
		}
		return "nao sei";
	}

	@Override
	public boolean finalAnswer(String answer) {
		lastAnswer = answer.equalsIgnoreCase(this.animal);
		return lastAnswer;
	}
	

}
