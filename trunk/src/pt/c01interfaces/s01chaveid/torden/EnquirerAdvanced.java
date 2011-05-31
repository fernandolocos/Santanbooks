package pt.c01interfaces.s01chaveid.torden;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;



public class EnquirerAdvanced implements IEnquirer {
	private IBaseConhecimento base;
	private Hashtable<String, Pergunta> tablePerguntas;
	
	// Tabela nome/perguntas restantes.
	private Hashtable<String, Integer> tableAnimais;
	private LinkedList<String> pilhaCandidatos;
	
	public EnquirerAdvanced() {
		base = new BaseConhecimento();
		tablePerguntas = new Hashtable<String, Pergunta>(80, 0.75f);
		tableAnimais = new Hashtable<String, Integer>(80, 0.75f);
		pilhaCandidatos = new LinkedList<String>();
		
		String[] nomesAnimais = base.listaNomes();
		
		for (String nomeAnimal : nomesAnimais) {
			IObjetoConhecimento obj = base.recuperaObjeto(nomeAnimal);
			for (IDeclaracao decl = obj.primeira(); 
					decl != null; 
					decl = obj.proxima()) {
				String pergunta = decl.getPropriedade();
				
				// Procura pela pergunta, ou insere-a como nova.
				Pergunta perguntaObj;
				if (tablePerguntas.containsKey(pergunta))
					perguntaObj = tablePerguntas.get(pergunta);
				else {
					perguntaObj = new Pergunta(pergunta);
					tablePerguntas.put(pergunta, perguntaObj);
				}

				// Adiciona nova pergunta para o animal.
				int count = tableAnimais.containsKey(nomeAnimal) ?
							tableAnimais.get(nomeAnimal) : 0;
				tableAnimais.put(nomeAnimal, count + 1);
				
				// Adiciona novo animal para a pergunta.
				perguntaObj.addAnimal(nomeAnimal, decl.getValor());
					
			}
			
		}
		
	}
	
	/**
	 * Método para selecionar a melhor pergunta a ser feita, dentre
	 * as que ainda estão em tablePerguntas.
	 * @return próxima pergunta a ser feita.
	 */
	private Pergunta selecionaMelhorPergunta() {
		Pergunta melhorPergunta = null;
		Enumeration<Pergunta> elementos = tablePerguntas.elements();
		while (elementos.hasMoreElements()) {
			Pergunta perguntaObj = elementos.nextElement();
			if (melhorPergunta == null || 
				melhorPergunta.getNumAnimais() < perguntaObj.getNumAnimais())
				melhorPergunta = perguntaObj;
		}
		
		return melhorPergunta;
	}
	
	/**
	 * Para cada animal de uma das listas de perguntaObj, para cada
	 * pergunta que tem esse animal, remove o animal da pergunta, possivelmente
	 * removendo a pergunta se esta não possui mais animais.
	 * @param perguntaObj
	 * @param valor
	 */
	private void removeAnimais(Pergunta perguntaObj, String valor) {
		LinkedList<String> listaAnimais = perguntaObj.getLista(valor);
		for (String nomeAnimal : listaAnimais) {
			IObjetoConhecimento obj = base.recuperaObjeto(nomeAnimal);
			for (IDeclaracao decl = obj.primeira(); decl != null;
						decl = obj.proxima()) {
				Pergunta pergunta = tablePerguntas.get(decl.getPropriedade());
				if (pergunta != null) {
					pergunta.removeAnimal(nomeAnimal, decl.getValor());
					if (pergunta.getNumAnimais() <= 0)
						tablePerguntas.remove(pergunta.getString());
				}
			}
		}
	}
	
	private String atualizaListaBoa(Pergunta perguntaObj, String valorPergunta) {
		String novoCandidato = null;
		LinkedList<String> listaAnimais = perguntaObj.getLista(valorPergunta);
		for (String nomeAnimal : listaAnimais) {
			if (tableAnimais.containsKey(nomeAnimal)) {
				int numPerguntasRestantes = tableAnimais.get(nomeAnimal);
				numPerguntasRestantes--;
				if (numPerguntasRestantes <= 0)
					novoCandidato = nomeAnimal;
				tableAnimais.put(nomeAnimal, numPerguntasRestantes);
			}
		}
		
		return novoCandidato;
	}
	
	/**
	 * Método principal. Faz a pergunta através do responder e atualiza
	 * as estruturas de dados principais, o que inclui eliminar animais
	 * que não coincidem com a resposta obtida, eliminar perguntas que
	 * não estão mais ligadas a nenhum animal e colocar novos candidatos
	 * em pilhaCandidatos.
	 * @param perguntaObj
	 */
	private void fazPergunta(Pergunta perguntaObj, IResponder responder) {
		String valorPergunta = responder.ask(perguntaObj.getString());
		
		// Remove todos os animais que tinham essa pergunta.
		if (valorPergunta.equalsIgnoreCase("nao sei")) {
			removeAnimais(perguntaObj, "sim");
			removeAnimais(perguntaObj, "nao");
		} else {
			String badValor = valorPergunta.equalsIgnoreCase("sim") ?
								"nao" : "sim";
			removeAnimais(perguntaObj, badValor);
			String novoCandidato = atualizaListaBoa(perguntaObj, valorPergunta);
			if (novoCandidato != null)
				pilhaCandidatos.push(novoCandidato);
		}
		
	}
	
	@Override
	public void connect(IResponder responder) {
		while (!tablePerguntas.isEmpty()) {
			Pergunta perguntaObj = selecionaMelhorPergunta();
			fazPergunta(perguntaObj, responder);
			tablePerguntas.remove(perguntaObj.getString());
		}
		
		String respostaFinal = null;
		try {
			respostaFinal = pilhaCandidatos.pop();
		} catch (NoSuchElementException exception) {
			respostaFinal = null;
		}
		
		if (respostaFinal != null)
			responder.finalAnswer(respostaFinal);
		else
			responder.finalAnswer("Unknowlingson");
		
	}
	
	

}
