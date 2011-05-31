package pt.c03ensaios.linnaeus;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * This component supplies simplified data from a single animal.
 * 
 * @author Oscar dos Santos Esgalha Neto
 * @author Wesley Tetsuya Schabert Takiguti Ide
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalData>")
public interface IAnimalData extends ISupports {
	/**
	 * @param
	 * None
	 * 
	 * @return
	 * A string that contains the name of the animal
	 */
	public String getNome();
	/**
	 * @param
	 * None
	 * 
	 * @return
	 * An integer which represents the number of questions
	 * that the animal contains
	 */
	public int getNperguntas();
	/** 
	 * @param 
	 * pergunta -> A string that contains an question
	 * @return
	 * A string with the answer to the given question
	 */
	public String getResposta(String pergunta);
	/**
	 * @param
	 * None
	 * @return
	 * An array with strings, the questions of the animal.
	 */
	public String[] getPerguntas();
}
