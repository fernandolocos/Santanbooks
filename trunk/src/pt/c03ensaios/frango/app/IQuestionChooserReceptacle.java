package pt.c03ensaios.frango.app;

import pt.c03ensaios.pizza.IQuestionChooser;
import anima.component.IReceptacle;

/**
 * Interface feita pra prover a conexão com a IQuestionChooser
 * 
 * @author Maurício Bertanha
 * @author Rodrigo Elizeu Gonçalves
 * 
 */
public interface IQuestionChooserReceptacle extends IReceptacle {
	public void connect(IQuestionChooser questionChooser);
}
