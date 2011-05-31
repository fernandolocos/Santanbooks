package pt.c03ensaios.afinder.animalinfo;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Handle the animals database with this component.
 * Requires AnimalPix, FileGUIComponent and Searcher.
 * (FileGUIComponent requires FileTxtHandler and Handler)
 *  
 * @author Renato Cesar Martins
 * @author Matheus Smythe Svolenski
 * 
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.afinder.animalinfo.IAnimalInfo>")
public interface IAnimalInfo extends ISupports, IReceptacleAnimalPix, IReceptacleFileGUIComponent, IReceptacleSearcher {
	
	/**
	 * Show the interface which lets the user handle 
	 * information about the animals in the database.
	 */
	public void showFrame ();
}
