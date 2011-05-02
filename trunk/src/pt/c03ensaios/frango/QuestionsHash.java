package pt.c03ensaios.frango;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Manage the questions that where answered by the animals
 * 
 * @author Maurício Bertanha
 * @author Rodrigo Elizeu Gonçalves
 *
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>", provides = { "<http://purl.org/dcc/pt.c03ensaios.frango.IQuestionsHash>" })
public class QuestionsHash extends ComponentBase implements IQuestionsHash {
	private HashMap<String, List<String>> hash;
	private List<String> questions;
	private int numberOfQuestions;

	/**
	 * Constructor
	 */
	public QuestionsHash() {
		questions = new ArrayList<String>();
		hash = new HashMap<String, List<String>>();
		numberOfQuestions = 0;
	}

    /**
     * Get a list of animals which answered the question 
     * @param question String
     * @return List<String> 
     */
	public List<String> getAnimals(String question) {
		return hash.get(question);
	}

	/**
	 * Put the animal into the hash
	 * @param question String
	 * @param animal String
	 */
	public void putAnimal(String question, String animal) {
		// Get a list of animals from the question
		List<String> list = getAnimals(question);

		// Verify if the list is null, and if it is, create a new list
		if (list == null) {
			list = new ArrayList<String>();
			questions.add(question);
			numberOfQuestions++;
		}

		// Add the animal to the list
		list.add(animal);

		// Put the list in the hash
		hash.put(question, list);
	}

	/**
	 * Remove the animal from the hash
	 * @param question String
	 * @param animal String
	 * @return boolean
	 */
	public boolean removeAnimals(String question, String animal) {
		boolean result = false;

		// Get a list of animals from the question
		List<String> list = getAnimals(question);

		// Verify if the list is null, and if it isn't, try to remove the animal
		if (list != null) {
			result = list.remove(animal);
			if (list.size() == 0) {
				questions.remove(question);
				numberOfQuestions--;
			}
		}

		return result;
	}

	/**
	 * Get the Questions answered
	 * @return List<String>
	 */	
	public List<String> getQuestions() {
		return questions;
	}

	/**
	 * Get the Number of Questions answered
	 * @return int
	 */
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	/**
	 * Get an array of String of animals which answered the question
	 * @param question String
	 * @return String[]
	 */
	public String[] getArrayAnimals(String question) {
		return (String[]) getAnimals(question).toArray();
	}
}
