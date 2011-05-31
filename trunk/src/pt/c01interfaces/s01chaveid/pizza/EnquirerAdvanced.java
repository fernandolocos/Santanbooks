package pt.c01interfaces.s01chaveid.pizza;

import java.util.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

public class EnquirerAdvanced implements IEnquirer
{
	class Question {
		public String text;
		public Vector<Integer> yeps, nops;
		
		public Question() {
			this.yeps = new Vector<Integer>();
			this.nops = new Vector<Integer>();
		}
		
		public Question(String text) {
			this.text = text;
			this.yeps = new Vector<Integer>();
			this.nops = new Vector<Integer>();
		}
	}
	
	String animals[];
	Vector<String> animals2;
	Vector<Question> questions;
	BaseConhecimento base;
	
	// Inicializa a base de dados e popula o vetor de animals, nosso conjunto-universo.
	public EnquirerAdvanced(){
		base = new BaseConhecimento();
		animals = base.listaNomes();
		questions = new Vector<Question>();
		
		Arrays.sort(animals); // HA!
	}
	
	public void popQuestions(){
		long start = System.currentTimeMillis();
		IDeclaracao dec;
		IObjetoConhecimento obj;
		Hashtable<String, Question> hash = new Hashtable<String, Question>();
		
		questions = new Vector<Question>();
		
		// Passa por todos os animals pegando suas questões e adicionando-as a questions[]
		for (int animal = 0; animal < animals.length; animal++) {
			obj = base.recuperaObjeto(animals[animal]);
			dec = obj.primeira();
			
			while (dec != null) {
				Question q = new Question(dec.getPropriedade());
				
				if (hash.containsKey(q.text) == false){
					hash.put(q.text, q);
					questions.add(q);
				}
						
				// Adicionada a pergunta, hora de ver se o animal corrente responde sim ou não
				if (dec.getValor().equalsIgnoreCase("sim")) {
					Question aux = hash.get(q.text);
					aux.yeps.add(animal);
				}
				else {
					Question aux = hash.get(q.text);
					aux.nops.add(animal);
				}
				
				dec = obj.proxima();
			}
		}
		
		for(int i = 0; i < questions.size(); i++)
		{
			Question aux = questions.get(i);
			aux = hash.get(aux.text);
		}
		
		long running_time = System.currentTimeMillis() - start;
		System.out.println("popQuestions() ran in " + running_time + "ms.");
	}

	public int bestQuestion() {
		// Decidimos qual é a melhor pergunta passando pelos vetores yeps e nops somando o tamanho de suas
		// listas para uma i-ésima pergunta e dividindo isso pelo total de animals ainda possiveis.
		// A pergunta que mais se aproximar de 50% vence a seletiva. 
		int best_index = 0;
		float var0 = 100, var1;
		
		for (int i = 0; i < questions.size(); i++) {
			Question current_question = questions.get(i);
			
			if ((current_question.yeps.size() + current_question.nops.size()) != animals.length) {	// ta checando o quê aqui, animal? 
				var1 = Math.abs(((current_question.yeps.size() + current_question.nops.size())) / animals.length - 0.5f);
				
				if (var1 < var0) {
					var0 = var1;
					best_index = i;
				}
			}
		}
		return best_index;	
	}
	
	public void connect(IResponder responder) {	
		while(animals.length > 1) {
			String answer;		// retorno de IResponder.Ask(String)
			int best_index;		// índice da melhor pergunta
			
			popQuestions();		// Popula os vetores questions[], yeps[] e nops[], nossos metadados de animals[].

			// O best_index armazena o índice da melhor pergunta a ser feita. É nossa pergunta escolhida.
			best_index = bestQuestion();

			// Mas se essa pergunta for respodida com sim ou não por TODOS os animais do conjunto-universo,
			// então significa que essa pergunta já não serve para descrever o conjunto.
			while (questions.get(best_index).yeps.size() == animals.length || questions.get(best_index).nops.size() == animals.length) {
				questions.remove(best_index);
				best_index = bestQuestion();
			}
			
			boolean diminished = false;
			
			while (diminished == false) {
				answer = responder.ask(questions.get(best_index).text);
				
				if (answer.equalsIgnoreCase("sim")) {
					String new_animals_set[] = new String[questions.get(best_index).yeps.size()];
					
					for (int i = 0; i < questions.get(best_index).yeps.size(); i++)
						new_animals_set[i] = animals[questions.get(best_index).yeps.get(i)];
					
					
					if (this.animals.length == new_animals_set.length) {
						questions.remove(best_index);
						best_index = bestQuestion();
					}
					else {
						diminished = true;
						this.animals = new_animals_set;
					}
				}
				else if (answer.equalsIgnoreCase("nao")) {
					String new_animals_set[] = new String[questions.get(best_index).nops.size()];
					
					for (int i = 0; i < questions.get(best_index).nops.size(); i++)
						new_animals_set[i] = animals[questions.get(best_index).nops.get(i)];
					
					if (this.animals.length == new_animals_set.length) {
						questions.remove(best_index);
						best_index = bestQuestion();
					}
					else {
						diminished = true;
						this.animals = new_animals_set;
					}
				}
				else { // There, there, sweetie, I really DONT HAVE ANY CLUE of what in the hell you're talking about!				
					// Vamos fazer assim: declaro um novo vetor de animais que tem o tamanho do original menos a soma dos yeps e nopes
					Vector<String> new_animals_set = new Vector<String>(animals.length - (questions.get(best_index).yeps.size() + questions.get(best_index).nops.size()));
					Question best_question = questions.get(best_index);
					
					// dai itero animals adicionando todos os que estiverem em animals mas NAO em yeps e nops praquela questao
					for (int animal = 0; animal < animals.length; animal++) {
						if ( !( best_question.yeps.contains(animal) || best_question.nops.contains(animal) ) )
							new_animals_set.add(animals[animal]);
					}
					
					// Voi là, this.animals = new_animals_set
					if (this.animals.length == new_animals_set.size()) {
						questions.remove(best_index);
						best_index = bestQuestion();
					}
					else {
						diminished = true;
						this.animals = (String[])new_animals_set.toArray(new String[new_animals_set.size()]);
					}
				}
			}
		}
	
		if (responder.finalAnswer(animals[0]))
			System.out.println("Certo!");
		else
			System.out.println("Fodeu!");
		
		System.out.println(animals[0]);
	}
}
