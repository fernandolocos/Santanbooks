package pt.c03ensaios.pizza;

import anima.annotation.ComponentInterface;

/**
 * Defines the interface of a question as used by Pizza to process 
 * animal sets.
 * 
 * @author Giuliano Roberto Pinheiro
 * @author Lucas Dermonde Gonçalves
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestion>")
public interface IQuestion {
	/**
	 * @return The question's text as it appears in the database.
	 */
	public String getQuestionText();
	
	public void incrementYeps();
	
	public void incrementNopes();
	
	public int getYeps();

	public int getNopes();
}
