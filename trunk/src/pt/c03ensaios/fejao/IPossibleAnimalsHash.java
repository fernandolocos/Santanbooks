package pt.c03ensaios.fejao;

import java.util.List;

import pt.c03ensaios.frango.IQuestionsHash;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface responsible for the control of animals still have a chance to be the animal sought
 * @author Fernando Costa e João Scalett
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimals>")
public interface IPossibleAnimalsHash extends ISupports{

	/**
	 * Connects an IQuestionHash object and sets it to the local arguments.
	 * The first time it is called, connects the hash of answers "Yes". Iterates internally and the next two times
	 * it is called, connects the hash of answers "No" and the hash of answers "Don't Know", respectively. If called after
	 * hash of answers "Don't Know" is already connected, restarts the process, from the hash of answers "Yes".
	 * 
	 * @param hash IQuestionsHash object to be connected.
	 */
	public void connect(IQuestionsHash hash);
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "Yes"
	 */
	public IQuestionsHash getQuestionsHashYes();
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "No"
	 */
	public IQuestionsHash getQuestionsHashNo();
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "Don't Know"
	 */
	public IQuestionsHash getQuestionsHashDontKnow();
	
	/**
     * Get a list of possible animals in the hash 
     * @return list of Strings, containing the names of the possible animals 
     */
	public List<String> getPossibleAnimalsList();
	
	/**
     * Get an array of possible animals in the hash 
     * @return array of Strings, containing the names of the possible animals 
     */
	
	public String[] getPossibleAnimalsArray();
	
	/**
     * Sets a new list of possible animals
     * @param animals list of Strings, containing names of animals
     */
	public void setPossibleAnimalsList(List<String> animals);
	
	/**
     * Sets a new list of possible animals
     * @param animals array of Strings, containing names of animals
     */
	public void setPossibleAnimalsArray(String[] animals);
	
	/**
	 * Resets the list of possible animals, so that all animals in the QuestionHashes
	 * become possible again.
	 */
	public void clearPossibleAnimalsList();

	/**
     * Adds new animals to the list of possible animals, preserving the current animals
     * @param animals list of Strings, containing names of animals
     */
	public void addAnimalsList(List<String> animals);
	
	/**
     * Adds new animals to the list of possible animals, preserving the current animals
     * @param animals array of Strings, containing names of animals
     */
	public void addAnimalsArray(String[] animals);
	
	/**
     * Removes animals from the list of possible animals, if animals received exist
     * @param animals list of Strings, containing names of animals
     */
	public void removeAnimalsList(List<String> animals);
	
	/**
     * Removes animals from the list of possible animals, if animals received exist
     * @param animals array of Strings, containing names of animals
     */
	public void removeAnimalsArray(String[] animals);
	
	/**
     * Gets the size of the list of possible animals, 
     * which is the number of possible animals.
     *  
     * @return number of animals. 
     */
	public int getNumberOfAnimals();

	/**
     * Determines the possible animals that are previously possible and which also answers 
     * the question received with the same answer received by parameter.
     * 
     * @param question String of the question asked 
     * @param answer String of the answer given to the question
     */
	public void DeterminesPossibleAnimals(String question, String answer);
}
