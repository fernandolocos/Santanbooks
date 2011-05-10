package pt.c03ensaios.fejao;

import pt.c03ensaios.frango.IQuestionsHash;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IReceptacleQuestionsHash>")
public interface IRecptacleQuestionsHash extends IReceptacle {
	public void connect(IQuestionsHash hash);
}
