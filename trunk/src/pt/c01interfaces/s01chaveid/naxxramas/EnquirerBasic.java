package pt.c01interfaces.s01chaveid.naxxramas;

import java.util.Hashtable;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer {
	private IBaseConhecimento bc;
	private String animals[];

	public EnquirerBasic() {
		this.bc = new BaseConhecimento();
		this.animals = this.bc.listaNomes();
	}

	@Override
	public void connect(IResponder responder) {
		IObjetoConhecimento obj = this.bc.recuperaObjeto(this.animals[0]);
		IDeclaracao decl = obj.primeira();
		Hashtable<String, String>h = new Hashtable<String, String>();
		String pergunta, resposta;
		int i = 0;

		while (decl != null) {
			pergunta = decl.getPropriedade();
			resposta = h.get(pergunta);
			if (resposta == null) {
				resposta = responder.ask(pergunta);
				h.put(pergunta, resposta);
			}

			if (resposta.equalsIgnoreCase(decl.getValor()))
				decl = obj.proxima();
			else {
				obj = this.bc.recuperaObjeto(this.animals[++i]);
				decl = obj.primeira();
			}
		}
		
		String animal = this.animals[i];
		this.showAnswer(responder.finalAnswer(animal), animal);
	}
	
	private void showAnswer(boolean acertou, String animal) {
	    if (acertou)
            System.out.println("Oba! Acertei! O animal eh " + animal + "!");
        else
            System.out.println("fuem! fuem! fuem! Achei que o animal era "  + animal + "...");
	}
}