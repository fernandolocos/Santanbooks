package pt.c03ensaios.frango.app;

import pt.c03ensaios.pizza.IQuestionChooser;
import anima.component.IReceptacle;

/**
 * Interface feita pra prover a conex�o com a IQuestionChooser
 * 
 * @author Maur�cio Bertanha
 * @author Rodrigo Elizeu Gon�alves
 * 
 */
public interface IQuestionChooserReceptacle extends IReceptacle {
	public void connect(IQuestionChooser questionChooser);
}
