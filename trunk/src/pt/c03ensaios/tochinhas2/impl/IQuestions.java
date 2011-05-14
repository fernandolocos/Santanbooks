package pt.c03ensaios.tochinhas2.impl;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import java.util.Vector;

/**
 * Interface that provides a simple handler for the questions - including 
 * filtration and keyword searches on them.
 * 
 * @author Alexandre Irmao Faltz and Davi Costa Clemente
 *
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>")
public interface IQuestions extends ISupports {
	/**
	 * Converts all instances in a Vector<String> to a simple array.
	 * 
	 * @param v The Vector<String> to be converted.
	 * @return All strings in array format.
	 */
	public String[] vectorToArray(Vector<String> v);
	
	/**
	 * Gets all questions on the current base.
	 * 
	 * @return All questions (without repetition).
	 */
	public Vector<String> getQuestions();
	
	/**
	 * Gets all questions of the specified animals.
	 * 
	 * @param animals The Vector<String> with the animals to get the questions.
	 * @return All questions (without repetition).
	 */
	public Vector<String> getQuestions(Vector<String> animals);
	
	/**
	 * Gets all questions of the specified animals.
	 * 
	 * @param animals The String[] with the animals to get the questions.
	 * @return All questions (without repetition).
	 */
	public Vector<String> getQuestions(String[] animals);
	
	/**
	 * Search on all questions with the specified keywords.
	 * 
	 * @param keys The keywords to search.
	 * @return The results of the search.
	 */
	public Vector<String> keywordSearch(Vector<String> keys);
	
	/**
	 * Search on all questions with the specified keywords.
	 * 
	 * @param keys The keywords to search.
	 * @return The results of the search.
	 */
	public Vector<String> keywordSearch(String[] keys);
	
	/**
	 * Get the number of times a selected keyword is being used.
	 * 
	 * @param key The key to get evaluated.
	 * @return The number of times the current keyword id being used.
	 */
	public int keywordReferences(String key);
	
	/**
	 * Get the answer to a question for an animal.
	 * 
	 * @param question The question to get the answer.
	 * @param animal The animal that will have the question answered.
	 * @return The answer!
	 */
	public String getAnswer(String question, String animal);
	
	/**
	 * Filters the base with a question, selecting ONLY the animals 
	 * that have this question.
	 * 
	 * @param question The question to be the filter.
	 * @return The animals that have the question.
	 */
	public Vector<String> baseFiltration(String question);
	
	/**
	 * Filters the base with a pair of question and answer, selecting
	 * ONLY the animals that have the correct pair.
	 * 
	 * @param question The question to be the filter.
	 * @param answer The answer to the selected question.
	 * @return The animals that have the question.
	 */
	public Vector<String> baseFiltration(String question, String answer);
}
