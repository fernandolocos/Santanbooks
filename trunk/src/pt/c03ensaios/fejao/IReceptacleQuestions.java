package pt.c03ensaios.fejao;

import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IReceptacleQuestions>")
public interface IReceptacleQuestions extends IReceptacle{
	public void connect(IQuestions questions);
}
