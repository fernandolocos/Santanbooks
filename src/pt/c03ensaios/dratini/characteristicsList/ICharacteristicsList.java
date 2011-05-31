package pt.c03ensaios.dratini.characteristicsList;

import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c03ensaios.dratini.characteristicsList.Caracteristica;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for a Characteristics List thats lists all animals' characteristics,
 * counts the number of answers to each characteristic, and calculates its entropy.
 * 
 * @author Kamila Galvani
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.dratini.characteristicsList.ICharacteristicsList>")
public interface ICharacteristicsList extends ISupports
{
	/**
	 * Copies the list
	 * @return copy of the list
	 */
    public ICharacteristicsList copy();

    
    /**
     * Calculates the entropy of each characteristic in the list
     */
    public void calculateEntropy();
  
    
    /**
     * Resets all attributes of each characteristic in the list
     */
    public void resetCharacteristics();
    
    /**
	 * Selects the characteristic that best divides the animals in three categories
	 * @return best characteristic
	 */
    public Caracteristica selectBestEntropy();
    
    /**
	 * Sums the answers of animal to each characteristic in list
	 * @param animal the animal whose characteristics will be evaluated and added to the list
	 */
    public void sumAnswers2Characteristics(IObjetoConhecimento animal);
    
    /**
	 * Gets the next element in list. Returns null if there are no elements after the current.
	 * @return next characteristic
	 */
    public Caracteristica getNext();
    
    /**
	 * Gets the first element in list
	 * @return first characteristic
	 */
    public Caracteristica first();
    
}
