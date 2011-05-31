package pt.c03ensaios.bolo;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface that helps solve the animals question problem, by mainly manipulating vectors.
 * 
 * @author Eric Oakley
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.bolo.IAnimalz>")
public interface IAnimalz extends ISupports{
	
	/**
	 * Return the vector of all the animals in the database.
	 * @return vector of all the animals
	 */
	public String[] getVetorDeAnimais();
	
	/**
	 * Return the vector of all the questions in the database, with no repetitions.
	 * @return vector of all the questions
	 */
	public String[] getVetorDePerguntas();
	
	/**
	 * Return the vector containing the priority of the questions, that is, how many animals have that question.
	 * @return vector of priorities
	 */
	public int[] getPrioridadeDePerguntas();
	
	/**
	 * Given a vector of valid animals, returns a string containing the next 
	 * question that must be asked to exclude the maximum number of animals. 
	 * The ideal question has 1/3 ‘sim’ answers, 1/3 ‘nao’ answers, and 1/3 ‘nao sei’ answers.
	 * @param animaisValidos vector of valid animals
	 * @return a question
	 */
	public String getProximaPergunta(boolean[] animaisValidos);
	
	/**
	 * Given a question, returns a vector with all the animals that have that question. 
	 * @param pergunta a question
	 * @return vector of animals
	 */
	public String[] getAnimaisQuePossuemPergunta(String pergunta);
	
	/**
	 * Given a question and a vector of valid animals, returns the score of the question 
	 * (can be utilized to obtain the next question which should be made). 
	 * Lower values indicate greater proximity to the ideal question.
	 * @param pergunta a question
	 * @param animaisValidos vector of valid animals
	 * @return vector of the score of each question
	 */
	public float getPontuacaoDaPergunta(String pergunta, boolean[] animaisValidos);
	
	/**
	 * Given a string containing a question, returns the position of the 
	 * questions vector which has that question. Returns -1 if the question is not found.
	 * @param pergunta a question
	 * @return a position of the questions vector
	 */
	public int convertePergunta(String pergunta);
	
	/**
	 * Given a string containing an animal, returns the position of the
	 * animals vector which has that animal. Returns -1 if the animal is not found.
	 * @param animal an animal
	 * @return a position of the animals vector
	 */
	public int converteAnimal(String animal);
	
}