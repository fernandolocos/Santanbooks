package pt.c03ensaios.pizza;

import java.util.Hashtable;
import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.*;

/**
 * This class represents a single thread that executes a part of the popQuestions() method.
 * It consumes an animal from the base, loops through its questions and checks the container 
 * for the question and increment the statistics according to the answers of the animal.
 * 
 * @author Giuliano Roberto Pinheiro
 * @author Lucas Dermondes Gonçalves
 */
public final class PizzaThread extends Thread {
	private PizzaThreadPool container;
	private final Vector<IQuestion> questions;			// reference to the main instance question vector
	private final Hashtable<String, IQuestion> hash;		// reference to the main instance hashtable
	private final IObjetoConhecimento objAnimal;		// the object that represents the animal
	
	public PizzaThread(IObjetoConhecimento objAnimal, Hashtable<String, IQuestion> ht, Vector<IQuestion> questions) {
		this.objAnimal = objAnimal;
		this.hash = ht;
		this.container = null;
		this.questions = questions;
	}
	
	public void setPool(PizzaThreadPool value) {
		this.container = value;
	}
	
	public PizzaThreadPool getPool() {
		return this.container;
	}
	
	public void run() {
		IDeclaracao dec = objAnimal.primeira();
		
		while (dec != null) {
			Question q = new Question(dec.getPropriedade());
			
			if (hash.containsKey(q.text) == false){
				synchronized(hash) {
					hash.put(q.text, q);
					questions.add(q);
				}
			}
					
			// Adicionada a pergunta, hora de ver se o animal corrente responde sim ou não
			if (dec.getValor().equalsIgnoreCase("sim")) {
				synchronized(hash) {
					IQuestion aux = hash.get(q.text);
					aux.incrementYeps();
				}
			}
			else {
				synchronized(hash) {
					IQuestion aux = hash.get(q.text);
					aux.incrementNopes();
				}
			}
			
			dec = objAnimal.proxima();
		}
		
		this.container.ThreadDies();
	}
}