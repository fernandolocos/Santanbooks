package pt.c03ensaios.torden;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Provides the services implemented in the QuestionMemory class.
 * The goal of this component is to provide means of retrieving the
 * pairs (question, answer) for all the questions asked by an IEnquirer
 * implementation.
 * 
 * @author Waldir Rodrigues de Almeida
 * 
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.torden.IQuestionMemory>")
public interface IQuestionMemory extends ISupports {
	
	/**
	 * Returns an String[][2] array of String arrays, whose rows represents 
	 * a pair (question, answer). For example, the entries [2][0] and
	 * [2][1] accesses the question at position 2 and its answer, respectively.
	 * @return A String[][2] if there are any pairs registered; null otherwise.
	 */
	public String[][] getAllAnsweredQuestions();
	
	/**
	 * Returns the pair (question, answer) at position n; i.e.,
	 * the n-th answered question and its answer.
	 * @param n
	 * @return 	a String[2] with the question at position 0 and 
	 * 			the answer at position 1 if n is a valid position; returns
	 * 			null otherwise;
	 */
	public String[] getQuestionAt(int n);
	
	/**
	 * Registers a new pair (question, answer) internally. This is supposed
	 * to be called from an Enquirer implementation, or something similar.
	 * @param question
	 * @param answer
	 */
	public void putNewAnsweredQuestion(String question, String answer);
	
	/**
	 * Clears everything, discarding any question/answer pair being stored.
	 */
	public void clear();

}
