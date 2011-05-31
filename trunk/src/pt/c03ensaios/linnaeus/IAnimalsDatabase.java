package pt.c03ensaios.linnaeus;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * This component supplies simplified data from the animals collection.
 * 
 * @author Oscar dos Santos Esgalha Neto
 * @author Wesley Tetsuya Schabert Takiguti Ide
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>")
public interface IAnimalsDatabase extends ISupports {
	/**
	 * @param
	 * None
	 * @return
	 * An int that represents the numbers of existing animals
	 */
	public int getNAnimais();
	/**
	 * @param
	 * None
	 * @return
	 * An array that contains all the animals, each one represented by the
	 * object of another component. Their methods can be founded at IAnimalData.
	 */
	public IAnimalData[] getAnimais();
	/**
	 * @param
	 * None
	 * @return
	 * An array containing strings with the names of the animals.
	 */
	public String[] getNomes();
	/**
	 * @param
	 * nome -> A string containing the name of an animal
	 * @return
	 * The animal that is requested in a IAnimalData object type.
	 */
	public IAnimalData getAnimal(String nome);
	/**
	 * @param
	 * None
	 * @return
	 * An string array that contains all the questions from the database.
	 * Without repetition.
	 */
	public String[] getPerguntas();
}
