package pt.c03ensaios.lobo.inter;

import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>")
public interface IReceptacleQuestions extends IReceptacle
{
	public void connect(IQuestions provider);
}
