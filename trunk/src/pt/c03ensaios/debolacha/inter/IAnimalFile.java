package pt.c03ensaios.debolacha.inter;

import java.util.Hashtable;

import pt.c03ensaios.linnaeus.*;

import anima.component.ISupports;
import anima.annotation.ComponentInterface;

/**
 * Interface of a component that stores an animal’s
 * data in a hashtable and write it in the database.
 * 
 * @author José Américo Nabuco Leva Ferreira de Freitas
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalFile>")
public interface IAnimalFile extends ISupports {
	
	/**
	 * Add a property and its value in a hashtable.
	 * @param property: Animal's property.
	 * @param value: Property’s value.
	 */
	public void addProperty(String property, String value);
	
	/**
	 * Remove a property from the hashtable.
	 * @param property: Animal's property.
	 */
	public void removeProperty(String property);
	
	/**
	 * Create a .txt file and store it in the base folder
	 */
	public void baseInsert();
	
	/**
	 * Sets name.
	 * @param name: Animal's name.
	 */
	public void setName(String name);
	
	/**
	 * Gets name.
	 * @return animal's name.
	 */
	public String getName();

	/**
	 * Gets hashtable<property, value>
	 * @return hashtable.
	 */
	public Hashtable<String, String> getAnimal();
	
	/**
	 * Sets base folder pathname.
	 * @param directory
	 */
	public void setDirectory(String directory);
	
	/**
	 * @return base folder pathname.
	 */
	public String getDirectory();
	
	/**
	 * @return true if AnimalFile's name is available.
	 */
	public boolean availableName();
	
	/**
	 * Check if the properties stored in the hashtable doesn't makes the database inconsistent.
	 * @param animals an object that implements IAnimalsDatabase interface.
	 * @return true if the animal's properties are unavailable.
	 */
	public boolean availableProperties();
	
	/**
	 * Compare the animals described by the properties stored in the hashtable with 
	 * an animal described by an object that implements IAnimalData interface.
	 * @param other: other animal (stored in IAnimalData object)
	 * @return true if both have the same properties.
	 */
	public boolean compareAnimals(IAnimalData other);
	
	/**
	 * Removes all animals' properties.
	 */
	public void removeAllProperties();
}
