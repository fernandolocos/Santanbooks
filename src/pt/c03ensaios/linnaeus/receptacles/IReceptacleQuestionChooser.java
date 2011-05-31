package pt.c03ensaios.linnaeus.receptacles;

import pt.c03ensaios.pizza.IQuestionChooser;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.receptacles.IReceptacleQuestionChooser>")
public interface IReceptacleQuestionChooser extends IReceptacle{
	public void connect(IQuestionChooser QuestionChooser);
}
