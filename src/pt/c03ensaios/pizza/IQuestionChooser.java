package pt.c03ensaios.pizza;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Returns the best question to be asked to a IResponder so the given animal set 
 * is roughly (the nearest possible) reduced by the half.
 *
 * @author Giuliano Roberto Pinheiro
 * @author Lucas Dermonde Gonçalves
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestionChooser>")
public interface IQuestionChooser extends ISupports {
	/** 
	 * @param Vector<IQuestion> containing the questions to be processed.
	 * @param int telling how many animals are being considered.
	 * @return String with the best question to be asked.
	 */
	public String BestQuestion(Vector<IQuestion> questions, int animal_count);
	
	/**
	 * @param BaseConhecimento, already instanciated, that access the considered database.
	 * @param String array with the universe-set of animals being considered.
	 * @return String with the best question to be asked.
	 */
	public String BestQuestion(IBaseConhecimento base, String[] animals);
}
