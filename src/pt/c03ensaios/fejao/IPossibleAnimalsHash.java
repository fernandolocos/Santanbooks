package pt.c03ensaios.fejao;

import java.util.List;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface responsible for the control of animals still have a chance to be the animal sought
 * @author Fernando Costa e João Scalett
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimals>")
public interface IPossibleAnimalsHash extends ISupports{

	 /**
     * Put a list of possible animals in the hash 
     * @param animals
     */
	public void putPossibleAnimalsList(List<String> animals);
	
	 /**
     * Get a list of possible animals in the hash 
     * @return List<String> list
     */
	public List<String> getPossibleAnimalsList();
	
	 /**
     * Get the list size 
     * @return int size
     */
	public int getListSize();
	
	 /**
     * Determines possible animals from the response to a new question
     * @param question, answer
     */
	public void DeterminesPossibleAnimals(String question, String answer);
}
