package pt.c03ensaios.frango;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * 
 * @author mbertanha
 * 
 */
@Component(id = "<http://purl.org/dcc/pt.c02foundations.frango.QuestionsHash>", provides = { "<http://purl.org/dcc/pt.c02foundations.frango.IQuestionsHash>" })
public class QuestionsHash extends ComponentBase implements IQuestionsHash {
	private HashMap<String, List<String>> hash;
	private List<String> questions;
	private int numberOfQuestions;

	public QuestionsHash() {
		questions = new ArrayList<String>();
		hash = new HashMap<String, List<String>>();
		numberOfQuestions = 0;
	}

	public List<String> getAnimals(String question) {
		return hash.get(question);
	}

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

	public boolean removeAnimals(String question, String animal) {
		boolean result = false;

		// Get a list of animals from the question
		List<String> list = getAnimals(question);

		// Verify if the list is null, and if it isn't, try to remove the animal
		if (list != null) {
			result = list.remove(question);
		}

		return result;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public String[] getArrayAnimals(String question) {
		return (String[]) getAnimals(question).toArray();
	}
}
