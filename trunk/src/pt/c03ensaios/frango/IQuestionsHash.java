package pt.c03ensaios.frango;

import java.util.List;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Manage the questions that where answered by the animals
 * 
 * @author Maurício Bertanha and Rodrigo Elizeu Gonçalves
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.frango.IQuestionsHash>")
public interface IQuestionsHash extends ISupports{

    /**
     * Get a list of animals which answered the question 
     * @param question
     * @return  
     */
	public List<String> getAnimals(String question);

	/**
	 * Put the animal into the hash
	 * @param question
	 * @param animal
	 */
	public void putAnimal(String question, String animal);

	/**
	 * Remove the animal from the hash
	 * @param question
	 * @param animal
	 * @return
	 */
	public boolean removeAnimals(String question, String animal);
	
	/**
	 * Get the Questions answered
	 * @return
	 */
	public List<String> getQuestions();
	
	/**
	 * Get the Number of Questions answered
	 * @return
	 */
	public int getNumberOfQuestions();
	
	/**
	 * Get an array of String of animals which answered the question
	 * @param question
	 * @return
	 */
	public String[] getArrayAnimals(String question);
}
