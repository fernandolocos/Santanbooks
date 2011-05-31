package pt.c03ensaios.afinder.animalpix;

import java.awt.Image;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Handle pictures of the animals with this component.
 *  
 * @author Renato Cesar Martins
 * @author Matheus Smythe Svolenski
 *
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.IAnimalPix>")
public interface IAnimalPix extends ISupports{

	/**
	 * Load the picture of the animal in an Image object.
	 * 
	 * @param animal	name of the animal
	 * @return			Image object containing the picture of the animal
	 */
	public Image getImage(String animal); 
	
	/**
	 * Verifies if there is an image of the animal in the database.
	 * 
	 * @param animal	name of the animal
	 * @return			true, if the image exists, 
	 * 					or false, if the image does not exist.
	 */
	public boolean hasImage(String animal);
	
	/**
	 * Adds a new picture from the hard disk to the database.
	 * 
	 * @param source	path to the picture that is going to be added
	 * @param animal	name of the animal 
	 * @return			true, if the image was successfully added, 
	 * 					or false, if the image could not be added
	 */
	public boolean setImageFromDisk (String source, String animal);
	
	/**
	 * Adds a new picture from the web to the database.
	 * 
	 * @param source	url of the picture that is going to be added
	 * @param animal	name of the animal 
	 * @return			true, if the image was successfully added, 
	 * 					or false, if the image could not be added
	 */
	public boolean setImageFromWeb (String source, String animal);
	
	/**
	 * Open a window that shows an image of the selected animal,
	 * with an interface for adding new images.
	 * 
	 * @param animal 	name of the animal
	 */
	public void showImageWindow (String animal);
	
}
