package pt.c01interfaces.s01chaveid.torden.old;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Enumeration;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

/*
 * EnquirerAdvanced
 * 
 * A ideia aqui e' fazer as perguntas que mais aparecem nos animais da base, para eliminar
 * candidatos rapidamente, enquanto e' mantida uma fila de prioridade que prioriza
 * os animais com menor numero de perguntas restantes. Se o prioritario tiver apenas
 * uma pergunta restante, faz essa pergunta e observa o resultado. 
 */
public class EnquirerAdvanced implements IEnquirer {
	private Hashtable<String, Pergunta> pergTable;
	private LinkedList<Pergunta> pergList;
	private PriorityQueue<Animal> animalPQ;
	private IBaseConhecimento base;
	
	/*
	 * Construtor. Preenche as estruturas de dados com os dados da Base.
	 */
	public EnquirerAdvanced() {
		base = new BaseConhecimento();
		String[] listaAnimais = base.listaNomes();
		
		pergTable = new Hashtable<String, Pergunta>(40, 0.75f);
		animalPQ = new PriorityQueue<Animal>();
		
		/* Preenche pergTable e animalPQ.
		 */
		for (String nome : listaAnimais) {
			IObjetoConhecimento objeto = base.recuperaObjeto(nome);
			Animal animal = new Animal(nome);
			for (IDeclaracao decl = objeto.primeira();
									decl != null;
										decl = objeto.proxima()) {
				
				String propriedade = decl.getPropriedade();
				String valor = decl.getValor();
				Pergunta pergunta = null;
				if (pergTable.containsKey(propriedade)) {
					pergunta = pergTable.get(propriedade);
				} else {
					pergunta = new Pergunta(propriedade);
					pergTable.put(propriedade, pergunta);
				}
				pergunta.insereAnimal(animal, valor);
				pergunta.incrementaPeso();
				animal.incrementaPerguntas();
			}
			
			animalPQ.add(animal);	
		}
		
		/* Preenche a lista e ordena por peso.
		 */
		pergList = new LinkedList<Pergunta>();
		Enumeration<Pergunta> elementos = pergTable.elements();
		while (elementos.hasMoreElements()) {
			pergList.add(elementos.nextElement());
		}
		Collections.sort(pergList);
	}

	/*
	 * Remove de animalPQ todos os animais que nao possuem a pergunta ou
	 * cujo valor da propriedade e' diferente. Para os demais, decrementa
	 * o numero de perguntas ainda nao respondidas.
	 */
	private void modificaAnimalPQ (Pergunta pergunta, String valorResposta) {
		if (valorResposta.equalsIgnoreCase("nao sei")) {
			LinkedList<Animal> lista = pergunta.getList("sim");
			for (Animal animal : lista)
				animalPQ.remove(animal);
			lista = pergunta.getList("nao");
			for (Animal animal : lista)
				animalPQ.remove(animal);
		} else {
			String badValor = valorResposta.equalsIgnoreCase("sim") ?
								"nao" : "sim";
			LinkedList<Animal> goodLista = pergunta.getList(valorResposta);
			LinkedList<Animal> badLista = pergunta.getList(badValor);
			for (Animal animal : goodLista) {
				// testa se ele ainda esta na PQ
				if (animalPQ.remove(animal)) {
					animal.decrementaPerguntas();
					animalPQ.add(animal);
				}
			}
			for (Animal animal : badLista)
				animalPQ.remove(animal);
		}
	}
	
	
	public void connect(IResponder responder) {
		
		String animalResposta = null;
		
		// Lista que mantem os animais candidatos. Necessaria para selecionar
		// o mais especifico.
		LinkedList<Animal> respostas = new LinkedList<Animal>();
		
		while (animalResposta == null && animalPQ.peek() != null) {
			if (animalPQ.size() == 1 && respostas.size() < 1) {
				
				animalResposta = animalPQ.peek().getNome();
				
			} else if (animalPQ.peek().getPergRestantes() < 2) {
				// Se o numero de perguntas do animal com menos perguntas restantes
				// for 1, faz essa pergunta e observa o resultado. Se a resposta
				// coincidiu, inclui esse animal no "topo" da lista de hipoteses.
				// No final, os animais que cuja resposta nao se verifica sao eliminados.
				// O processo considera a possibilidade de haver animais mais especificos,
				// com mais perguntas, que continuam sendo adicionados na lista de hipoteses.

				Animal animal = animalPQ.poll();
				IObjetoConhecimento objeto = base.recuperaObjeto(animal.getNome());
				
				// procura a pergunta restante
				Pergunta pergunta = null;
				boolean achou = false;
				IDeclaracao decl = objeto.primeira();
				while (!achou && decl != null) {
					if (pergTable.containsKey(decl.getPropriedade())) {
						pergunta = pergTable.get(decl.getPropriedade());
						achou = true;
					} else {
						decl = objeto.proxima();
					}
				}
				
				if (decl != null) {
					// Invocacao do responder
					String respostaPropriedade = responder.ask(pergunta.getPropriedade());
					if (respostaPropriedade.equalsIgnoreCase(decl.getValor())) {
						respostas.addLast(animal);
					} else {
						modificaAnimalPQ(pergunta, respostaPropriedade);
						pergTable.remove(pergunta.getPropriedade());
						pergList.remove(pergunta);
					}
				} else {
					respostas.addLast(animal);
				}
	
				
			} else {
				// Seleciona e faz a proxima pergunta que mais ocorre nos animais da base.
				// Com a resposta obtida, elimina os animais que nao possuem a pergunta
				// ou cujo valor da propriedade e' outro e decrementa o numero de perguntas
				// restantes para os animais cujo valor da propriedade coincide com a resposta.
				
				Pergunta pergunta = pergList.poll();
				
				// Caso excepcional em que se esgotaram as perguntas e nenhum animal combinou.
				// Nao se espera que aconteca.
				if (pergunta == null) {
					animalResposta = "nao sei";
					break;
				}
				
				pergTable.remove(pergunta.getPropriedade());
				
				// Invocacao do responder
				String respostaPropriedade = responder.ask(pergunta.getPropriedade());
				modificaAnimalPQ(pergunta, respostaPropriedade);
			}
		}
		
		// Seleciona o animal no "topo" da lista, que deve
		// ser o que "coincide mais" com as respostas.
		if (animalResposta == null)
			animalResposta = respostas.getLast().getNome();
		
		/* TESTE
		if (responder.finalAnswer(animalResposta)) {
			System.out.println("ACERTOU " + animalResposta);
		} else {
			System.out.println("ERROU. CHUTOU " + animalResposta);
		}
		*/
		
		responder.finalAnswer(animalResposta);
		
	}

}
