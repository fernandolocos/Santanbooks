package pt.c03ensaios.lobo.inter;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for a Graphics User Interface component that provides simplicity at creating new
 * Animal files, which will be added to the Animal Database.
 * This component is a GUI for IAnimalFile.
 * Requires IAnimalFile and IQuestion.
 * 
 * @author <b>Rodrigo Lobo da Costa - RA 104012</b>
 * @author <b>Fabio Beranizo Fontes Lopes - RA 104864</b>
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IAnimalCreatorGUI>")
public interface IAnimalCreatorGUI extends IReceptacleAnimalFile, IReceptacleQuestions, IReceptacleNaxxramas,ISupports
{
	/**
	 * <b>Initializes this component.</b><br>
	 * This method must be called after connecting with an <i>IAnimalFile</i> and an <i>IQuestion</i>.
	 */
	public void initialize();
	
	/**
	 * <b>Sets the test mode true or false.</b><br>
	 * If <i>true</i>, the animal will never be added to the Data Base, even if its name and properties are
	 * available (this is useful for testing). If <i>false</i>, the animal can be added to the Data Base normally.<br><br>
     * *Default value: <i>true</i>
	 * @param value true: sets the test mode on; false: sets the test mode off
	 */
	public void setTestMode(boolean value);
}
