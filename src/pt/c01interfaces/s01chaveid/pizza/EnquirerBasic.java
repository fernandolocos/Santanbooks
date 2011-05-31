package pt.c01interfaces.s01chaveid.pizza;

import java.util.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;

public class EnquirerBasic implements IEnquirer
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
	public EnquirerBasic(){
		base = new BaseConhecimento();
		animals = base.listaNomes();
		questions = new Vector<Question>();
		
		Arrays.sort(animals); // HA!
	}
	
	public void popQuestions(){
		IDeclaracao dec;
		IObjetoConhecimento obj;
		
		questions = new Vector<Question>();
		
		// Passa por todos os animals pegando suas questões e adicionando-as a questions[]
		for (int animal = 0; animal < animals.length; animal++) {
			obj = base.recuperaObjeto(animals[animal]);
			dec = obj.primeira();
			
			while (dec != null) {
				Question q = new Question(dec.getPropriedade());
				int index = -1;
				
				for (int i = 0; i < questions.size(); i++) {
					if (questions.get(i).text.equalsIgnoreCase(q.text)) index = i; 
				}
				
				if (index == -1) {
					questions.add(q);
					index = questions.size() - 1;
				}
				
				//index = questions.indexOf(q); // index = questions.size();?
				
				// Adicionada a pergunta, hora de ver se o animal corrente responde sim ou não
				if (dec.getValor().equalsIgnoreCase("sim")) {
					questions.get(index).yeps.add(animal);
				}
				else {
					questions.get(index).nops.add(animal);
				}
				
				dec = obj.proxima();
			}
		}
	}

	public int bestQuestion() {
		// Escolhe a primeira pergunta cuja resposta nao seja identica pra todos os animais 
		int fast_index = 0;
	
		for (int i = 0; i < questions.size(); i++) {
			Question current_question = questions.get(i);
			
			if ((current_question.yeps.size() != animals.length)&&(current_question.nops.size() != animals.length)){
				fast_index =i;
				i = questions.size()+2;
			}
		}
		return fast_index;
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
			System.out.println("Fodeo!");
		
		System.out.println(animals[0]);
	}
}
