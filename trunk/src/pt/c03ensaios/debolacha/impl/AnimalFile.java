package pt.c03ensaios.debolacha.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import pt.c03ensaios.debolacha.inter.IAnimalFile;
import pt.c03ensaios.linnaeus.*;

/**
 * Stores an animal’s data in a hashtable and write it in the database.
 * 
 * @author José Américo Nabuco Leva Ferreira de Freitas
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalFile>",
		   provides = {"<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalFile>"})
public class AnimalFile extends ComponentBase implements IAnimalFile, IRequires<IAnimalsDatabase> {

	private Hashtable<String, String> animal;
	private String name;
	private String directory;
	IAnimalsDatabase animalData;
	
	/**
	 * Constructor.
	 * @param name: Animal's name.
	 */
	public AnimalFile(){
		this.animal = new Hashtable<String, String>();
		this.directory = "pt/c01interfaces/s01chaveid/s01base/bd";
	}
	
	/**
	 * Adds a property and its value in a hashtable.
	 * @param property: Animal's property.
	 * @param value: Property’s value.
	 */
	@Override
	public void addProperty(String property, String value) {
		animal.put(property, value);
	}

	/**
	 * Remove a property from the hashtable.
	 * @param property: Animal's property.
	 */
	public void removeProperty(String property){
		animal.remove(property);
	}
	
	/**
	 * Creates a .txt file and store it in the base folder.
	 * @param value: Base’s relative address from the binary file.
	 */
	@Override
	public void baseInsert() {
		File newAnimal = new File(directory+"\\"+name+".txt");

		/*Creates an String[] that stores the animal's properties*/
		String[] properties = getAnimal().keySet().toArray(new String[0]);
		
		/*Creates the file*/
		try {
			newAnimal.createNewFile();
		} catch (IOException e) {
			System.err.println("This animal already exists!");
			e.printStackTrace();
		}
		
		try {
			BufferedWriter newProperty = new BufferedWriter( new FileWriter(newAnimal));
			
			/*Write the animal's properties in the file*/
			for(int i=0; i < properties.length; i++){
				newProperty.write("\""+properties[i]+"\", \""+getAnimal().get(properties[i])+"\"");
				newProperty.newLine();
			}
			
			newProperty.close();
		} catch (IOException e) {
			System.err.println("Error while writing in database.");
			e.printStackTrace();
		}
	}

	/**
	 * Sets name.
	 * @param name: Animal's name.
	 */
	public void setName(String name){
		this.name = name.toLowerCase();
	}
	
	/**
	 * Gets name.
	 * @return animal's name.
	 */
	public String getName(){
		return this.name;	
	}

	/**
	 * Gets hashtable<property, value>
	 * @return hashtable.
	 */
	public Hashtable<String, String> getAnimal(){
		return this.animal;
	}
	
	/**
	 * Sets base folder pathname.
	 * @param directory
	 */
	public void setDirectory(String directory){
		this.directory = directory;
	}
	
	/**
	 * @return base folder pathname.
	 */
	public String getDirectory(){
		return directory;
	}
	
	/**
	 * @return true if AnimalFile's name is available.
	 */
	public boolean availableName(){
		boolean available = true;
		File folder = new File(directory);
		
		for( String aux: folder.list()){
			if(aux.equals(name.concat(".txt")))
				available = false;
		}

		return available;
	}
	
	/**
	 * Check if the properties stored in the hashtable doesn't makes the database inconsistent.
	 * @param animals an object that implements IAnimalsDatabase interface.
	 * @return true if the animal's properties are unavailable.
	 */
	public boolean availableProperties(){
		boolean available = true;
		
		for( IAnimalData aux: animalData.getAnimais())
			if(this.compareAnimals(aux))
				available = false;

		return available;
	}

	/**
	 * Compare the animals described by the properties stored in the hashtable with 
	 * an animal described by an object that implements IAnimalData interface.
	 * @param other: other animal
	 * @return true if both have the same properties.
	 */
	@SuppressWarnings("unchecked")
	public boolean compareAnimals(IAnimalData other){
		String[] pergs = other.getPerguntas();
		if(pergs.length != this.getAnimal().keySet().toArray().length)
			return false;
		
		Vector<String> questions = new Vector<String>();
		Hashtable<String, String> aux = (Hashtable<String, String>)this.getAnimal().clone();
		
		for(int i = 0; i< pergs.length; i++)
			questions.add(pergs[i]);
		
		for(int i = 0; i< questions.size(); i++){
			if(other.getResposta(questions.get(i)).equals(aux.get(questions.get(i)))){
				aux.remove(questions.get(i));
				questions.remove(questions.get(i--));
			}
		}
		if(aux.isEmpty() && questions.isEmpty())
			return true;
		
		return false;
	}

	/**
	 * Removes all animals' properties.
	 */
	public void removeAllProperties(){
		this.animal = new Hashtable<String, String>();
	}
	
	@Override
	public void connect(IAnimalsDatabase arg0) {
		animalData = arg0;
	}
}
