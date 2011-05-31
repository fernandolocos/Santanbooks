package pt.c01interfaces.s01chaveid.torden.old;

import java.util.Hashtable;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

/*
 * EnquirerBasic.
 */
public class EnquirerBasic implements IEnquirer {
	private IBaseConhecimento base;
	private Hashtable<String, String> tabela;
	private String[] nomesAnimais;
	
	public EnquirerBasic() {
		base = new BaseConhecimento();
		tabela = new Hashtable<String, String>(30, 0.75f);
		nomesAnimais = base.listaNomes();
	}

	public void connect(IResponder responder) {
		String animal = null;
		for (int i = 0; i < nomesAnimais.length && animal == null; i++) {
			if (testaAnimal(nomesAnimais[i], responder))
				animal = nomesAnimais[i];	
		}
		
		/* TESTE
		if (animal != null)
			if (responder.finalAnswer(animal))
				System.out.println("Acertou " + animal);
		else
			responder.finalAnswer("Unknowlingson");
		*/
		
		if (animal != null)
			responder.finalAnswer(animal);
		else
			responder.finalAnswer("Unknowlingson");
	}
	
	
	boolean testaAnimal(String animal, IResponder responder) {
		IObjetoConhecimento objeto = base.recuperaObjeto(animal);
		
		boolean falhou = false;
		for (IDeclaracao decl = objeto.primeira(); 
							decl != null && !falhou; 
								decl = objeto.proxima()) {
			
			String pergunta = decl.getPropriedade();
			
			if (!tabela.containsKey(pergunta))
				// chamada do Responder
				tabela.put(pergunta, responder.ask(pergunta));
			
			if (!decl.getValor().equalsIgnoreCase(tabela.get(pergunta)))
				falhou = true;
		}
		
		return !falhou;
	}
	
}

