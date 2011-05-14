package pt.c03ensaios.pizza;

import java.util.Hashtable;
import java.util.Vector;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import pt.c01interfaces.s01chaveid.s01base.inter.*;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>",
        provides={"<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestionChooser>"})
public final class QuestionChooser extends ComponentBase implements IQuestionChooser {
	private Hashtable<String, Question> hash;
	private boolean use_multithreading;
	
	/**
	 * Readonly property.
	 * @return The hashtable of the questions.
	 */
	public Hashtable<String, Question> getHashtable() {
		return this.hash;
	}
	
	/**
	 * Property. Says if a given isntance of QuestionChooser was instantiated 
	 * to use multithreading.
	 * @return Boolean value of the multithreading state.
	 */
	public boolean IsUsingMultithreading() {
		return this.use_multithreading;
	}
	
	/**
	 * Property. Sets the state of multithreading of the QuestionChooser instance.<br />
	 * <b>Advice:</b> Please, do not change this when the instance is already working.
	 * The result is not defined.
	 * @param value Value of the parameter to be set.
	 */
	public void SetMultithreadingState(boolean value) {
		this.use_multithreading = value;
	}
	
	/**
	 * Default constructor.
	 */
	public QuestionChooser() {
		this.hash = new Hashtable<String, Question>();
		this.use_multithreading = false;
	}
	
	/**
	 * @param base An already instantiated BaseConhecimento object. 
	 * @param animals A string representing the set of animals to be considered by the algorithm.
	 * @return Returns a Vector of an IQuestion implementation that contains all the questions asked 
	 * to that set of animals, no matter the set, and which animals answer (and how they answer) to 
	 * those questions.
	 */
	private final Vector<IQuestion> popQuestions(IBaseConhecimento base, String[] animals) {
		IObjetoConhecimento obj;
		Vector<IQuestion> questions;
		
		this.hash = new Hashtable<String, Question>();
		questions = new Vector<IQuestion>();
		
		for (int animal = 0; animal < animals.length; animal++) {
			IDeclaracao dec;
			obj = base.recuperaObjeto(animals[animal]);
			dec = obj.primeira();
			
			while (dec != null) {
				Question q = new Question(dec.getPropriedade());
				
				if (hash.containsKey(q.text) == false){
					hash.put(q.text, q);
					questions.add(q);
				}
						
				if (dec.getValor().equalsIgnoreCase("sim")) {
					Question aux = hash.get(q.text);
					aux.incrementYeps();
				}
				else {
					Question aux = hash.get(q.text);
					aux.incrementNopes();
				}
				
				dec = obj.proxima();
			}
		}
		
		for(int i = 0; i < questions.size(); i++)
		{
			IQuestion aux = questions.get(i);
			aux = hash.get(aux.getQuestionText());
		}
		
		return questions;
	}

	/**
	 * @param questions A Vector of an IQuestion implementation which contains the questions and metadata 
	 * about the answers of those questions. This is always relative to a given set of animals.
	 * @param animalCount How many animals the considered set contains.
	 * @return The string with the best question to be asked. 
	 */
	public final String BestQuestion(Vector<IQuestion> questions, int animalCount) {
		int best_index = 0;
		float best_rank = 100, rank = 99;
		
		for (int i = 0; i < questions.size(); i++) {
			IQuestion current_question = questions.get(i);
			
			// Essa condição assume que nem todos os animais respondem à essa pergunta, o que nos faz tentar dividir o conjunto ao meio 
			// tomando como critério os animais que respondem (sim ou não) e os que não respondem à pergunta.
			if ((current_question.getYeps() + current_question.getNopes()) != animalCount) { 
				rank = Math.abs( ( ((float)current_question.getYeps() + (float)current_question.getNopes()) ) / (float)animalCount - 0.5f );
				
				//System.out.println("[if]QUESTION: " + current_question.getQuestionText() + "; rank: " + rank);
				
				if (rank < best_rank) {
					best_rank = rank;
					best_index = i;
				}
			}
			else {
				rank = Math.abs((float)current_question.getYeps() / (float)animalCount - 0.5f);
				// This equivalent to using getNopes(), because Yeps = N - Nopes. Do the math =)
				
				//System.out.println("[else]QUESTION: " + current_question.getQuestionText() + "; rank: " + rank);
				
				if (rank < best_rank) {
					best_rank = rank;
					best_index = i;
				}
			}
		}
		
		return questions.get(best_index).getQuestionText();
	}

	/**
	 * @param base An already instantiated BaseConhecimento object.
	 * @param animals A string representing the set of animals to be considered by the algorithm.
	 * @return The string with the best question to be asked.
	 */
	public final String BestQuestion(IBaseConhecimento base, String[] animals) {
		Vector<IQuestion> q = popQuestions(base, animals);
		
		return BestQuestion(q, animals.length);
	}

}
